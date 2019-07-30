package top.vchar.demo.spring.rocketMQ.jms;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * <p> 消息消费者 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/29 22:43
 */
@Component
public class MsgConsumer {

    /**
     * 消息消费者
     */
    @Value("${apache.rocketmq.consumer.push-consumer}")
    private String pushConsumer;

    /**
     * NameServer 地址
     */
    @Value("${apache.rocketmq.namesrv-addr}")
    private String namesrvAddr;

    @PostConstruct
    public void defaultMQPushConsumer(){
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(pushConsumer);
        consumer.setNamesrvAddr(namesrvAddr);
        try {
            //设置consumer所订阅的Topic和Tag, *代表所有的Tag
            consumer.subscribe("testTopic", "*");

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
                        System.out.println("messageExt: "+messageExt);
                        String messageBody = new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET);
                        //输出消息内容
                        System.out.println("消费响应msgId: "+messageExt.getMsgId()+", msgBody: "+messageBody);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;//稍后再试
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;//消费成功
            });
            consumer.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
