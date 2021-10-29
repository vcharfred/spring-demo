package top.vchar.demo.spring.rocketMQ.controller;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.vchar.demo.spring.rocketMQ.jms.MsgProducer;

import java.nio.charset.StandardCharsets;

/**
 * <p>  TODO 功能描述 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/29 22:56
 */
@RestController
public class OrderController {

    @Autowired
    private MsgProducer msgProducer;

    @GetMapping("/order")
    public String order(String msg, String tag) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        Message message = new Message("testTopic", tag, msg.getBytes(StandardCharsets.UTF_8));
        SendResult result = msgProducer.getProducer().send(message);
        System.out.println("发送响应：MsgId: "+result.getMsgId()+", 发送状态："+result.getSendStatus());
        return result.toString();
    }




}
