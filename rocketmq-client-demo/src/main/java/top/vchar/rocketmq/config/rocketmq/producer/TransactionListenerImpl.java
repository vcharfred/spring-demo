package top.vchar.rocketmq.config.rocketmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * <p> 事务消息监听器实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/30
 */
@Slf4j
@Component
public class TransactionListenerImpl implements TransactionListener {

    private final ConcurrentHashMap<String, Integer> localTrans = new ConcurrentHashMap<>();

    /**
     * 在MQ将消息投递成功后会调用这个方法
     * @param msg 消息内容
     * @param arg 自定义的业务参数
     * @return 返回消息状态
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        String transactionId = msg.getTransactionId();
        log.info("执行本地事务:{}", transactionId);

        // 这里进行业务处理
        try{
            Thread.sleep(1000*10);
            localTrans.put(msg.getTransactionId(), 2);
            log.info("本地业务执行完成:{}", transactionId);
        }catch (Exception e){
            e.printStackTrace();
        }

        return LocalTransactionState.COMMIT_MESSAGE;
    }

    /**
     * 检查本地事务，这里就是补充机制，比如 executeLocalTransaction执行异常的时候，这里可以作为补偿处理
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        String transactionId = msg.getTransactionId();
        log.info("事务检查: {}", transactionId);
        Integer status = localTrans.get(transactionId);

        if (null != status) {
            localTrans.put(transactionId, 1);
            switch (status) {
                case 0:
                    return LocalTransactionState.UNKNOW;
                case 1:
                    return LocalTransactionState.COMMIT_MESSAGE;
                case 2:
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                default:
                    break;
            }
        }
        return LocalTransactionState.UNKNOW;
    }
}
