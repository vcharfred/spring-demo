package top.vchar.rocketmq.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import top.vchar.rocketmq.config.RocketProducerBuilder;
import top.vchar.rocketmq.dto.OrderDTO;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * <p> 消息推送示例 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/27
 */
@Slf4j
@RestController
@RequestMapping("/rocketmq-client")
public class MqMessageController {

    @Autowired
    private RocketProducerBuilder producerBuilder;

    /**
     * 普通消息
     */
    @PostMapping("/general")
    public Mono<SendResult> sendMessage(@RequestBody OrderDTO orderDTO){

        DefaultMQProducer producer = producerBuilder.build();
        Message message = new Message();
        message.setTopic("demo-pay");
        message.setTags("train");
        message.setKeys(UUID.randomUUID().toString());
        message.setBody(JSONObject.toJSONString(orderDTO).getBytes(StandardCharsets.UTF_8));
        return Mono.defer(()-> {
            try {
                return Mono.just(producer.send(message, 3000));
            } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
                e.printStackTrace();
                return Mono.error(e);
            }
        });

    }

    /**
     * 异步消息
     */
    @PostMapping("/async")
    public String asyncSend(@Validated @RequestBody OrderDTO orderDTO) {

        DefaultMQProducer producer = producerBuilder.build();
        Message message = new Message();
        message.setTopic("demo-pay");
        message.setTags("train");
        message.setKeys(UUID.randomUUID().toString());
        message.setBody(JSONObject.toJSONString(orderDTO).getBytes(StandardCharsets.UTF_8));
        try {
            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("异步消息发送结果：{}", sendResult);
                }
                @Override
                public void onException(Throwable e) {
                    log.error("异步消息发送异常：", e);
                }
            }, 3000);
        } catch (Exception e) {
            log.error("异步消息发送异常：", e);
        }
        return "发送成功";
    }

    /**
     * 单向消息
     */
    @PostMapping("/oneway")
    public String sendOneway(@Validated @RequestBody OrderDTO orderDTO) {

        DefaultMQProducer producer = producerBuilder.build();
        Message message = new Message();
        message.setTopic("demo-pay");
        message.setTags("train");
        message.setKeys(UUID.randomUUID().toString());
        message.setBody(JSONObject.toJSONString(orderDTO).getBytes(StandardCharsets.UTF_8));
        try {
            producer.sendOneway(message);
        } catch (Exception e) {
            log.error("单向消息发送异常：", e);
        }
        return "发送成功";
    }

    /**
     * 延时消息
     */
    @PostMapping("/delay")
    public SendResult delayMessage(@Validated @RequestBody OrderDTO orderDTO){

        Message message = new Message();
        message.setTopic("demo-pay");
        message.setTags("train");
        message.setKeys(UUID.randomUUID().toString());
        message.setBody(JSONObject.toJSONString(orderDTO).getBytes(StandardCharsets.UTF_8));
        // 延时等级(delayLevel)对应的时间 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h，从1开始
        message.setDelayTimeLevel(3);
        try {
            return producerBuilder.build().send(message);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("延时消息发送异常：", e);
        }
        return null;
    }

    /**
     * 顺序消息
     */
    @PostMapping("/order")
    public SendResult orderMessage(@Validated @RequestBody OrderDTO orderDTO){
        Message message = new Message();
        message.setTopic("demo-pay");
        message.setTags("train");
        message.setKeys(UUID.randomUUID().toString());
        message.setBody(JSONObject.toJSONString(orderDTO).getBytes(StandardCharsets.UTF_8));
        try {
            return producerBuilder.build().send(message);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("延时消息发送异常：", e);
        }
        return null;
    }


}
