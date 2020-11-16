package top.vchar.rocketmq.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.vchar.rocketmq.config.rocketmq.RocketProducerBuilder;
import top.vchar.rocketmq.dto.OrderNotify;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * <p> 事务消息 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/30
 */
@RestController
@RequestMapping("/transaction")
public class TransactionMessageController {

    private final RocketProducerBuilder producerBuilder;

    public TransactionMessageController(RocketProducerBuilder producerBuilder) {
        this.producerBuilder = producerBuilder;
    }

    /**
     * 发送事务消息
     */
    @PostMapping
    public TransactionSendResult sendTransactionMessage(@Validated @RequestBody OrderNotify orderNotify){


        try {
            Message message = new Message();
            message.setTopic("activity-notify");
            message.setTags("train");
            message.setKeys(UUID.randomUUID().toString());
            message.setBody(JSONObject.toJSONString(orderNotify).getBytes(StandardCharsets.UTF_8));

            return producerBuilder.build().sendMessageInTransaction(message, null);
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        return null;
    }

}
