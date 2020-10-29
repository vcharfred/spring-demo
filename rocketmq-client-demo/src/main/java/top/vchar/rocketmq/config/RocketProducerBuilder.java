package top.vchar.rocketmq.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

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

    /**
     * NameServer 地址
     */
    @Value(value = "${rocketmq.name-server}")
    private String nameServerAddr;

    /**
     * 生产者的组名
     */
    @Value(value = "${rocketmq.producer.group}")
    private String producerGroup;

    private DefaultMQProducer producer;

    /**
     * 初始化DefaultMQProducer
     *
     * 参考rocketmq-spring-boot包中的org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration类
     *
     * @throws MQClientException 启动消息生产者异常
     */
    @PostConstruct
    void init() throws MQClientException {
        //生产者的组名
        producer = new DefaultMQProducer(producerGroup);
        /// 指定NameServer地址，多个地址以 ; 隔开
        producer.setNamesrvAddr(nameServerAddr);
        // 关闭Channel通道
        producer.setVipChannelEnabled(false);
        // 发送消息超时时间，单位毫秒
        producer.setSendMsgTimeout(3000);
        // 在同步模式下，消息发送失败后重试次数，注意这个可能导致重复消息
        producer.setRetryTimesWhenSendFailed(2);
        // 在异步模式下，消息发送失败后重试次数，注意这个可能导致重复消息
        producer.setRetryTimesWhenSendAsyncFailed(2);
        // 发送消息的消息体网络包最大值
        producer.setMaxMessageSize(1024 * 1024 * 4);
        // 当消息体网络包大于4k时压缩消息
        producer.setCompressMsgBodyOverHowmuch(1024 * 4);
        // 当向一个broker发送消息失败了，是否重新尝试下一个
        producer.setRetryAnotherBrokerWhenNotStoreOK(false);
        // Producer对象在使用之前必须要调用start初始化，只能初始化一次
        producer.start();
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
