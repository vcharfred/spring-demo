package top.vchar.rocketmq.controller;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.Message;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.vchar.rocketmq.config.RocketProducerBuilder;
import top.vchar.rocketmq.dto.OrderNotify;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

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

    @PostMapping
    public String sendMessage(@Validated @RequestBody OrderNotify orderNotify){

        Message message = new Message();
        message.setTopic("order-notify");
        message.setTags("train");
        message.setKeys(UUID.randomUUID().toString());
        message.setBody(JSONObject.toJSONString(orderNotify).getBytes(StandardCharsets.UTF_8));
        message.setDelayTimeLevel(2);
        try {
            producerBuilder.build().send(message, (mqs, msg, arg) -> {
                // 这里就是进行队列的选择，这里的arg参数就是后面传入的那个参数
                int index = Math.abs(arg.hashCode()%mqs.size());
                return mqs.get(index);
            }, orderNotify.getOrderNo());
        } catch (Exception e) {
            log.error("顺序消息发送异常", e);
            return "顺序消息发送异常："+e.getMessage();
        }
        return "消息发送完成";
    }


}
