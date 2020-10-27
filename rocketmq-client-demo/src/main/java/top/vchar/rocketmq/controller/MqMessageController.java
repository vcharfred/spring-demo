package top.vchar.rocketmq.controller;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import top.vchar.rocketmq.config.RocketProducerBuilder;

/**
 * <p> 消息推送示例 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/27
 */
@RestController
@RequestMapping("/rocketmq-client")
public class MqMessageController {

    @Autowired
    private RocketProducerBuilder producerBuilder;

    @GetMapping("/")
    public Mono<SendResult> producerMessage(){
        DefaultMQProducer producer = producerBuilder.build();
        Message message = new Message();

        try {
            SendResult sendResult = producer.send(message);
            return Mono.just(sendResult);
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            e.printStackTrace();
            return Mono.error(e);
        }
    }

}
