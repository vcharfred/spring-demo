package top.vchar.client;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p> 自定义前缀拦截器 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/11/19
 */
public class PrefixProducerInterceptor implements ProducerInterceptor<String, String> {

    private AtomicInteger sendSuccess = new AtomicInteger(0);
    private AtomicInteger sendFailure = new AtomicInteger(0);

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> producerRecord) {
        // 给所有的value加上test前缀
        String modifiedValue = "test-"+producerRecord.value();
        // 重新组装消息记录
        return new ProducerRecord<>(producerRecord.topic(), producerRecord.partition()
                , producerRecord.timestamp(), producerRecord.key(), modifiedValue, producerRecord.headers());

    }

    @Override
    public void onAcknowledgement(RecordMetadata recordMetadata, Exception e) {
        if(e==null){
            sendSuccess.getAndIncrement();
        } else {
            sendFailure.getAndIncrement();
        }
    }

    @Override
    public void close() {
        BigDecimal successNum = new BigDecimal(String.valueOf(sendSuccess.get()));
        BigDecimal failureNum = new BigDecimal(String.valueOf(sendFailure.get()));
        double successRatio = successNum.multiply(new BigDecimal("100")).divide(successNum.add(failureNum),2, BigDecimal.ROUND_DOWN).doubleValue();
        System.out.println("发送成功率为："+successRatio+"%");
    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
