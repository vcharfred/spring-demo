package top.vchar.train.config.rocketmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import top.vchar.common.exception.BizRunTimeException;

/**
 * <p> 火车票支付消息者 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/27
 */
@Slf4j
public class TrainPayRocketConsumer extends AbstractRocketConsumer{


    private final String topics;

    private final String tags;

    public TrainPayRocketConsumer(String topics, String tags){
        this.topics = topics;
        this.tags = tags==null?"*":tags;
    }

    @Override
    public void init() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("TRAIN_SERVER_CONSUMER");
        consumer.setNamesrvAddr("192.168.111.63:9876");

        try {
            //设置consumer所订阅的Topic和Tag, *代表所有的Tag
            consumer.subscribe(this.topics, this.tags);

            // CONSUME_FROM_LAST_OFFSET 默认策略。从该队列最尾开始消费，跳过历史消息
            // CONSUME_FROM_FIRST_OFFSET, 从队列最开始开始消费，即历史消息（还存在broker的）全部消费一遍
            // CONSUME_FROM_TIMESTAMP;//根据时间消费
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

//            MessageListenerOrderly 有序的
            //MessageListenerConcurrently无序的，效率更高
            consumer.registerMessageListener((MessageListenerConcurrently)(list, context)->{
                try{
                    for(MessageExt messageExt:list){
                        //打印消息内容
                        log.info("messageExt: {}", messageExt);
                        String messageBody = new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET);
                        //输出消息内容
                        log.info("消费响应msgId: {}, msgBody: {}", messageExt.getMsgId(), messageBody);
                    }
                }catch (Exception e){
                    log.error("消息消费失败", e);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;//稍后再试
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;//消费成功
            });
            consumer.start();
            super.setMqPushConsumer(consumer);
            log.info("启动rocketmq 消费者");
        }catch (Exception e){
            throw new BizRunTimeException("注册rocketmq消费者异常", e);
        }
    }
}
