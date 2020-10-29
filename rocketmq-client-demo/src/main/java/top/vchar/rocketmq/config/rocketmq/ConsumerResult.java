package top.vchar.rocketmq.config.rocketmq;

import lombok.Data;

import java.io.Serializable;

/**
 * <p> 消费结果 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/29
 */
@Data
public class ConsumerResult implements Serializable {

    /**
     * 是否消息成功
     */
    private boolean success;

    /**
     * 是否重试，仅success为false时启用
     */
    private boolean retry = false;

    public ConsumerResult(boolean success){
        this.success = success;
    }

    public ConsumerResult(boolean success, boolean retry){
        this.success = success;
        this.retry = retry;
    }
}
