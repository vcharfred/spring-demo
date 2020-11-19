package top.vchar.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * <p>  TODO 功能描述 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/11/18
 */
@Slf4j
@RestController
@RequestMapping("/kafka")
public class IndexController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOP_NAME = "quickstart-events";

    @GetMapping
    public String index(String data) throws ExecutionException, InterruptedException {
        // 发送消息
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(TOP_NAME, data);
        log.info("发送完成:{}", future.get());
        return "ok";
    }

    /**
     * 接收消息
     * @param cr 消息
     * @throws Exception 异常
     */
    @KafkaListener(topics = TOP_NAME)
    public void listen(ConsumerRecord<?, ?> cr) throws Exception {
        log.info("接收到的消息：{}", cr.toString());
    }

}
