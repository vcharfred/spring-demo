package top.vchar.rocketmq.config.rocketmq.handler;

import top.vchar.rocketmq.config.rocketmq.ConsumerResult;

/**
 * <p> 普通消息延时消息业务handler </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/29
 */
public interface RocketConsumerHandler {

    /**
     * 消息处理
     * @param message 消息内容
     * @return 返回处理结果
     */
    ConsumerResult handler(ConsumerMessage message);

}
