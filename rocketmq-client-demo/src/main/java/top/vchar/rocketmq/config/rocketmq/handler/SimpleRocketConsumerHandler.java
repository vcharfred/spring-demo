package top.vchar.rocketmq.config.rocketmq.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.vchar.rocketmq.config.rocketmq.ConsumerResult;

/**
 * <p>  普通消息延时消息业务handler实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/29
 */
@Slf4j
@Component
public class SimpleRocketConsumerHandler implements RocketConsumerHandler {

    @Override
    public ConsumerResult handler(ConsumerMessage message) {
        log.info("消费消息: {}", message);
        return new ConsumerResult(true);
    }
}
