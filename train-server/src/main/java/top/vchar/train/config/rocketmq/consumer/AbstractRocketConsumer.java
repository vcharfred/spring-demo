package top.vchar.train.config.rocketmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.MQPushConsumer;

import java.util.Objects;

/**
 * <p> rocketmq 消费者基础信息 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/27
 */
@Slf4j
public abstract class AbstractRocketConsumer implements RocketConsumer {

    protected MQPushConsumer consumer;

    /**
     * 初始化
     */
    @Override
    public abstract void init();

    public void setMqPushConsumer(MQPushConsumer consumer){
        this.consumer = consumer;
    }

    /**
     * 销毁
     */
    public void destroy() {
        if (Objects.nonNull(consumer)) {
            consumer.shutdown();
        }
        log.info("container destroyed, {}", this.toString());
    }
}
