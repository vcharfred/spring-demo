package top.vchar.pay.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * <p> 事务消息 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/11/16
 */
@Slf4j
@Component
@RocketMQTransactionListener
public class PayMqTransactionListener implements RocketMQLocalTransactionListener {

    /**
     * 在MQ将消息投递成功后会调用这个方法
     * @param msg 消息内容
     * @param arg 自定义的业务参数
     * @return 返回消息状态
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        // TODO 执行业务逻辑
        System.out.println("执行本地事务");
        return RocketMQLocalTransactionState.COMMIT;
    }

    /**
     * 检查本地事务，这里就是补充机制，比如 executeLocalTransaction执行异常的时候，这里可以作为补偿处理
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        // TODO 执行业务检查
        System.out.println("消息补偿：检查本地事务");
        return RocketMQLocalTransactionState.UNKNOWN;
    }
}
