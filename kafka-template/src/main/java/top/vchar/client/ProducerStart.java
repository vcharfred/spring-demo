package top.vchar.client;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.Scanner;

/**
 * <p> 生成者启动类 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/11/19
 */
public class ProducerStart {

    public static void main(String[] args) {
        Properties properties = new Properties();
        // 设置key-value序列化器
        /// properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        /// properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // 设置重试次数
        properties.put(ProducerConfig.RETRIES_CONFIG, 3);
        // 设置集群地址
        /// properties.put("bootstrap.servers", "192.168.111.53:9092");
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.111.53:9092");
        // 设置分区器
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitioner.class.getName());
        // 添加自定义拦截器
        properties.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, PrefixProducerInterceptor.class.getName());


        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String next = scanner.nextLine();
            if("exit".equals(next)){
                break;
            }
            String[] arr = next.split("\\s+");
            ProducerRecord<String, String> record = new ProducerRecord<>("demo",  arr[0], arr[1]);
            try{
                // 发送同步消息
                RecordMetadata recordMetadata = producer.send(record).get();
                System.out.println("同步消息发送成功："+recordMetadata.toString());

                // 发送异步消息
                producer.send(record, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        System.out.println("异步消息发送成功："+recordMetadata.toString());
                    }
                });

                System.out.println("send message to kafka successful");
            }catch (Exception e){
                System.out.println(e.getMessage());
                break;
            }
        }

        producer.close();
    }
}
