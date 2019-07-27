package top.vchar.demo.spring.mq.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import top.vchar.demo.spring.mq.service.ProducerService;

import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.Topic;

/**
 * <p> 消息生产者实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/25 21:03
 */
@Service
public class ProducerServiceImpl implements ProducerService {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private Queue queue;

    /**
     * 发送消息
     * @param destination 指定发送到的队列
     * @param message 待发送的消息
     */
    @Override
    public void sendMessage(Destination destination, String message) {
        jmsMessagingTemplate.convertAndSend(destination, message);
    }

    /**
     * 发送消息
     * @param message 待发送的消息
     */
    @Override
    public void sendMessage(String message) {
        jmsMessagingTemplate.convertAndSend(this.queue, message);
    }


    //============发布订阅===========
    @Autowired
    private Topic topic;
    @Override
    public void publish(String msg){
        jmsMessagingTemplate.convertAndSend(this.topic, msg);
    }
}
