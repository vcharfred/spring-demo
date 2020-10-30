package top.vchar.rocketmq.config.rocketmq.handler;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * <p> 消息信息 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/30
 */
@Builder
@Data
public class ConsumerMessage implements Serializable {

    private Integer number;

    private String message;

}
