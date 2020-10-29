package top.vchar.rocketmq.config.rocketmq;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.MQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.beans.factory.DisposableBean;

import java.util.Objects;
import java.util.Optional;

/**
 * <p> rocketmq 消费者基础信息 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/27
 */
@Slf4j
public abstract class AbstractRocketConsumer implements RocketConsumer, DisposableBean {

    protected MQPushConsumer consumer;

    @Getter
    private final String nameServer;

    @Getter
    private final String consumerGroup;

    @Getter
    private final ConsumeFromWhere consumeFromWhere;

    public AbstractRocketConsumer(String nameServer, String consumerGroup, ConsumeFromWhere consumeFromWhere){
        this.nameServer = nameServer;
        this.consumerGroup = consumerGroup;
        this.consumeFromWhere = Optional.ofNullable(consumeFromWhere).orElse(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
    }

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
    @Override
    public void destroy() throws Exception {
        if (Objects.nonNull(consumer)) {
            consumer.shutdown();
        }
        log.info("container destroyed, {}", this.toString());
    }


}
