package top.vchar.rocketmq.config.rocketmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import top.vchar.common.exception.BizRunTimeException;
import top.vchar.rocketmq.config.rocketmq.ConsumerResult;
import top.vchar.rocketmq.config.rocketmq.handler.ConsumerMessage;
import top.vchar.rocketmq.config.rocketmq.handler.RocketConsumerHandler;

import java.util.Optional;

/**
 * <p> 顺序消息消息者 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/30
 */
@Slf4j
public class OrderRocketConsumer extends AbstractRocketConsumer {

    private final String topics;

    private final String tags;

    private final RocketConsumerHandler rocketConsumerHandler;

    public OrderRocketConsumer(String nameServer, String consumerGroup, String topics, String tags, RocketConsumerHandler rocketConsumerHandler) {
        super(nameServer, consumerGroup, null);
        this.topics = topics;
        this.tags = Optional.ofNullable(tags).orElse("*");
        this.rocketConsumerHandler = rocketConsumerHandler;
    }

    @Override
    public void init() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(getConsumerGroup());
        consumer.setNamesrvAddr(getNameServer());
        try {
            // 设置consumer所订阅的Topic和Tag, *代表所有的Tag
            consumer.subscribe(this.topics, this.tags);
            // CONSUME_FROM_FIRST_OFFSET, 从队列最开始开始消费，即历史消息（还存在broker的）全部消费一遍
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            // 设置线程数，默认是20；这里先将其设置小点
            consumer.setConsumeThreadMin(3);
            consumer.setConsumeThreadMax(6);

            // MessageListenerOrderly 有序的
            consumer.registerMessageListener((MessageListenerOrderly) (list, context) -> {
                try{
                    log.warn("本次消息数：{}", list.size());
                    for(MessageExt messageExt:list){
                        //打印消息内容
                        log.info("messageExt: [{}]: {}", getNumber(), messageExt);
                        String messageBody = new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET);
                        ConsumerResult consumerResult = rocketConsumerHandler.handler(ConsumerMessage.builder()
                                .number(getNumber())
                                .message(messageBody)
                                .build());
                        if(!consumerResult.isSuccess() && consumerResult.isRetry()){
                            return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                        }
                    }
                }catch (Exception e){
                    log.error("顺序消息消费异常：", e);
                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                }
                return ConsumeOrderlyStatus.SUCCESS;
            });
            super.setMqPushConsumer(consumer);
            consumer.start();
            log.info("start rocketmq consumer success");
        }catch (Exception e){
            throw new BizRunTimeException("注册rocketmq消费者异常", e);
        }
    }

    public static OrderRocketConsumer.OrderRocketConsumerBuilder builder(){
        return new OrderRocketConsumer.OrderRocketConsumerBuilder();
    }

    public static class OrderRocketConsumerBuilder {

        private String nameServer;

        private String consumerGroup;

        private String topics;

        private String tags;

        private RocketConsumerHandler rocketConsumerHandler;

        private Integer number;

        public OrderRocketConsumerBuilder(){

        }

        public OrderRocketConsumer build(){
            OrderRocketConsumer consumer = new OrderRocketConsumer(this.nameServer, this.consumerGroup, this.topics, this.tags, this.rocketConsumerHandler);
            consumer.setNumber(this.number);
            return consumer;
        }

        public OrderRocketConsumerBuilder nameServer(String nameServer){
            this.nameServer = nameServer;
            return this;
        }

        public OrderRocketConsumerBuilder consumerGroup(String consumerGroup){
            this.consumerGroup = consumerGroup;
            return this;
        }

        public OrderRocketConsumerBuilder topics(String topics){
            this.topics = topics;
            return this;
        }

        public OrderRocketConsumerBuilder tags(String tags){
            this.tags = tags;
            return this;
        }

        public OrderRocketConsumerBuilder rocketConsumerHandler(RocketConsumerHandler rocketConsumerHandler){
            this.rocketConsumerHandler = rocketConsumerHandler;
            return this;
        }

        public OrderRocketConsumerBuilder number(Integer number){
            this.number = number;
            return this;
        }

    }

}
