package top.vchar.rocketmq.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.vchar.rocketmq.config.RocketProducerBuilder;

import java.util.List;

/**
 * <p> 顺序消息 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/29
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderMessageController {

    private final RocketProducerBuilder producerBuilder;

    public OrderMessageController(RocketProducerBuilder producerBuilder) {
        this.producerBuilder = producerBuilder;
    }

    @GetMapping
    public String sendMessage(String uid){

        Message message = new Message();
        message.setTopic("order-notify");
        message.setTags("train");
        try {
            producerBuilder.build().send(message, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    return null;
                }
            }, uid);
        } catch (Exception e) {

            log.error("顺序消息发送异常", e);
        }

        return "消息发送完成";
    }

    private String buildMessage(String uid, String type){
        JSONObject data = new JSONObject();
        data.put("uid", uid);
        data.put("type", type);
        return data.toJSONString();
    }
}
