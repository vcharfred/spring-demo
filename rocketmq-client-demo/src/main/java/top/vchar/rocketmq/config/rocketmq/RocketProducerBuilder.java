package top.vchar.rocketmq.config.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.trace.AsyncTraceDispatcher;
import org.apache.rocketmq.client.trace.TraceDispatcher;
import org.apache.rocketmq.client.trace.hook.SendMessageTraceHookImpl;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.vchar.rocketmq.config.rocketmq.bean.RocketMQProperties;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;

/**
 * <p> 消息生产者 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/27
 */
@Slf4j
@Component
public class RocketProducerBuilder implements DisposableBean {

    private final RocketMQProperties properties;

    private TransactionMQProducer producer;

    @Autowired
    private TransactionListener transactionListener;

    public RocketProducerBuilder(RocketMQProperties properties) {
        this.properties = properties;
    }

    /**
     * 初始化DefaultMQProducer
     *
     * 参考rocketmq-spring-boot包中的org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration类
     *
     * @throws MQClientException 启动消息生产者异常
     */
    @PostConstruct
    void init() throws MQClientException {
        RocketMQProperties.Producer producerProperty = properties.getProducer();

        //生产者的组名
        this.producer = new TransactionMQProducer(producerProperty.getGroup());
        /// 指定NameServer地址，多个地址以 ; 隔开
        this.producer.setNamesrvAddr(properties.getNameServer());
        // 关闭Channel通道
        this.producer.setVipChannelEnabled(false);
        // 发送消息超时时间，单位毫秒
        this.producer.setSendMsgTimeout(3000);
        // 在同步模式下，消息发送失败后重试次数，注意这个可能导致重复消息
        this.producer.setRetryTimesWhenSendFailed(2);
        // 在异步模式下，消息发送失败后重试次数，注意这个可能导致重复消息
        this.producer.setRetryTimesWhenSendAsyncFailed(2);
        // 发送消息的消息体网络包最大值
        this.producer.setMaxMessageSize(1024 * 1024 * 4);
        // 当消息体网络包大于4k时压缩消息
        this.producer.setCompressMsgBodyOverHowmuch(1024 * 4);
        // 当向一个broker发送消息失败了，是否重新尝试下一个
        this.producer.setRetryAnotherBrokerWhenNotStoreOK(false);

        boolean isEnableMsgTrace = producerProperty.isEnableMsgTrace();
        String customizedTraceTopic = producerProperty.getCustomizedTraceTopic();

        if (isEnableMsgTrace) {
            try {
                AsyncTraceDispatcher dispatcher = new AsyncTraceDispatcher(producerProperty.getGroup()
                        , TraceDispatcher.Type.PRODUCE, customizedTraceTopic, null);
                dispatcher.setHostProducer(this.producer.getDefaultMQProducerImpl());
                Field field = DefaultMQProducer.class.getDeclaredField("traceDispatcher");
                field.setAccessible(true);
                field.set(this.producer, dispatcher);
                this.producer.getDefaultMQProducerImpl().registerSendMessageHook(new SendMessageTraceHookImpl(dispatcher));
            } catch (Throwable e) {
                log.error("system trace hook init failed ,maybe can't send msg trace data");
            }
        }
        this.producer.setTransactionListener(this.transactionListener);
        // Producer对象在使用之前必须要调用start初始化，只能初始化一次
        this.producer.start();
    }

    /**
     * 获取DefaultMQProducer
     * @return  返回消息生产者DefaultMQProducer
     */
    public DefaultMQProducer build(){
        return this.producer;
    }

    @Override
    public void destroy() throws Exception {
        if(null!=producer){
            producer.shutdown();
            log.info("Rocket Producer Destroyed");
        }
    }
}
