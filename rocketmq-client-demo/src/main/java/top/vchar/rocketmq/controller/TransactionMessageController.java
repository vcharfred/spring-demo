package top.vchar.rocketmq.controller;

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

        Message message = new Message();
        try {
            TransactionSendResult result = producerBuilder.build().sendMessageInTransaction(message, null);
            return result;
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        return null;
    }

}
