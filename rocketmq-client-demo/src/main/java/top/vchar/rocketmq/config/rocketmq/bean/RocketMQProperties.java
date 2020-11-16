package top.vchar.rocketmq.config.rocketmq.bean;

import lombok.Data;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.topic.TopicValidator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * <p> 配置信息 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/29
 */
@Data
@Component
@ConfigurationProperties(prefix = "rocketmq")
public class RocketMQProperties implements Serializable {

    /**
     * The name server for rocketMQ, formats: `host:port;host:port`.
     */
    private String nameServer;

    private Producer producer;

    private List<Consumer> consumer;

    @Data
    public static final class Producer {

        private String group;

        /**
         * Switch flag instance for message trace.
         */
        private boolean enableMsgTrace = true;

        /**
         * The name value of message trace topic.If you don't config,you can use the default trace topic name.
         */
        private String customizedTraceTopic = TopicValidator.RMQ_SYS_TRACE_TOPIC;
    }

    @Data
    public static final class Consumer {
        private String consumerGroup;

        private ConsumeFromWhere consumeFromWhere = ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET;

        private String topics;

        private String tags;

        private String rocketConsumerHandler = "top.vchar.rocketmq.config.rocketmq.handler.SimpleRocketConsumerHandler";

        /**
         * 类型
         * 0-普通消息
         * 1-顺序消息
         */
        private int type = 0;

        private Integer number;

    }
}
