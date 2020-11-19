package top.vchar.client;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;

/**
 * <p> 自动提交关闭的消费者 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/11/19
 */
public class ConsumerAutoCommitOffStart {

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.111.53:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "demo.two");
        // 设置手动提交，默认是true 自动提交
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        // 设置订阅的主题
        TopicPartition topicPartition = new TopicPartition("demo", 0);
        consumer.assign(Collections.singletonList(topicPartition));

        long lastConsumerOffset = -1;
        while (true){

            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            if(records.isEmpty()){
                break;
            }
            List<ConsumerRecord<String, String>> partitionRecords = records.records(topicPartition);
            lastConsumerOffset = partitionRecords.get(partitionRecords.size()-1).offset();
            // 同步提交消费位移
            consumer.commitAsync();
        }

        System.out.println("消费的偏移量:" + lastConsumerOffset);
        Map<TopicPartition, OffsetAndMetadata> committed = consumer.committed(Collections.singleton(topicPartition));
        System.out.println("提交的偏移量 :"+committed.get(topicPartition).offset());
        long position = consumer.position(topicPartition);
        System.out.println("下一个记录位置 : "+position);

    }

}
