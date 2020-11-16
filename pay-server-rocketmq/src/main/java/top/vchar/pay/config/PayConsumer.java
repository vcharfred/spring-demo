package top.vchar.pay.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * <p> 支付消费者 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/11/16
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "pay_consumer", topic = "pay-info", selectorExpression = "*")
public class PayConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("接收到的消息"+message);
    }
}
