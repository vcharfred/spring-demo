package top.vchar.rocketmq.config.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.util.Assert;
import top.vchar.common.exception.BizRunTimeException;
import top.vchar.rocketmq.config.rocketmq.handler.RocketConsumerHandler;

import java.util.Optional;

/**
 * <p> 普通消息，延时消息消费者 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/29
 */
@Slf4j
public class SimpleRocketConsumer extends AbstractRocketConsumer {

    private final String topics;

    private final String tags;

    private final RocketConsumerHandler rocketConsumerHandler;

    /**
     * 创建简单的消费者
     * @param nameServer name server
     * @param consumerGroup 消费者组
     * @param consumeFromWhere 消费策略
     *                         CONSUME_FROM_LAST_OFFSET 默认策略。从该队列最尾开始消费，跳过历史消息
     *                         CONSUME_FROM_FIRST_OFFSET, 从队列最开始开始消费，即历史消息（还存在broker的）全部消费一遍
     *                         CONSUME_FROM_TIMESTAMP; 根据时间消费
     * @param topics 主题
     * @param tags 标签，默认为*
     * @param rocketConsumerHandler  消息接收业务处理器
     */
    public SimpleRocketConsumer(String nameServer, String consumerGroup, ConsumeFromWhere consumeFromWhere, String topics, String tags, RocketConsumerHandler rocketConsumerHandler){
        super(nameServer, consumerGroup, consumeFromWhere);
        Assert.notNull(nameServer, "RocketMQ name server can't null");
        Assert.notNull(consumerGroup, "RocketMQ consumer group can't null");
        Assert.notNull(topics, "RocketMQ topics can't null");
        Assert.notNull(rocketConsumerHandler, "RocketMQ SimpleRocketConsumerHandler can't null");

        this.topics = topics;
        this.tags = Optional.ofNullable(tags).orElse("*");
        this.rocketConsumerHandler = rocketConsumerHandler;
    }

    @Override
    public void init() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(getConsumerGroup());
        consumer.setNamesrvAddr(getNameServer());
        try {
            //设置consumer所订阅的Topic和Tag, *代表所有的Tag
            consumer.subscribe(this.topics, this.tags);

            // CONSUME_FROM_LAST_OFFSET 默认策略。从该队列最尾开始消费，跳过历史消息
            // CONSUME_FROM_FIRST_OFFSET, 从队列最开始开始消费，即历史消息（还存在broker的）全部消费一遍
            // CONSUME_FROM_TIMESTAMP;//根据时间消费
            consumer.setConsumeFromWhere(getConsumeFromWhere());

//            MessageListenerOrderly 有序的
            //MessageListenerConcurrently无序的，效率更高
            consumer.registerMessageListener((MessageListenerConcurrently)(list, context)->{
                try{
                    for(MessageExt messageExt:list){
                        //打印消息内容
                        log.info("messageExt: {}", messageExt);
                        String messageBody = new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET);
                        ConsumerResult consumerResult = rocketConsumerHandler.handler(messageBody);
                        if(!consumerResult.isSuccess() && consumerResult.isRetry()){
                            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                        }
                    }
                }catch (Exception e){
                    log.error("消息消费异常", e);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
            consumer.start();
            super.setMqPushConsumer(consumer);
            log.info("start rocketmq consumer success");
        }catch (Exception e){
            throw new BizRunTimeException("注册rocketmq消费者异常", e);
        }
    }

    public static SimpleRocketConsumerBuilder builder(){
        return new SimpleRocketConsumerBuilder();
    }

    static class SimpleRocketConsumerBuilder {

        private String nameServer;

        private String consumerGroup;

        private ConsumeFromWhere consumeFromWhere;

        private String topics;

        private String tags;

        private RocketConsumerHandler rocketConsumerHandler;

        public SimpleRocketConsumerBuilder(){

        }

        public SimpleRocketConsumer build(){
            return new SimpleRocketConsumer(this.nameServer, this.consumerGroup, this.consumeFromWhere, this.topics, this.tags, this.rocketConsumerHandler);
        }

        public SimpleRocketConsumerBuilder nameServer(String nameServer){
            this.nameServer = nameServer;
            return this;
        }

        public SimpleRocketConsumerBuilder consumerGroup(String consumerGroup){
            this.consumerGroup = consumerGroup;
            return this;
        }

        public SimpleRocketConsumerBuilder consumeFromWhere(ConsumeFromWhere consumeFromWhere){
            this.consumeFromWhere = consumeFromWhere;
            return this;
        }

        public SimpleRocketConsumerBuilder topics(String topics){
            this.topics = topics;
            return this;
        }

        public SimpleRocketConsumerBuilder tags(String tags){
            this.tags = tags;
            return this;
        }

        public SimpleRocketConsumerBuilder rocketConsumerHandler(RocketConsumerHandler rocketConsumerHandler){
            this.rocketConsumerHandler = rocketConsumerHandler;
            return this;
        }

    }

}
