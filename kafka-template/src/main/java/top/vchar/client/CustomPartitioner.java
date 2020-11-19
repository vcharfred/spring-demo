package top.vchar.client;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p> 自定义分区器 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/11/19
 */
public class CustomPartitioner implements Partitioner {

    private final AtomicInteger counter = new AtomicInteger(0);

    /**
     * 分区选择策略
     * @param topic 主题
     * @param key key
     * @param keyBytes key的byte数组
     * @param value 值
     * @param valueBytes 值的byte数组
     * @param cluster 集群对象
     * @return 返回分区编号
     */
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {

        List<PartitionInfo> partitionInfos = cluster.partitionsForTopic(topic);
        int size = partitionInfos.size();
        if(null==keyBytes){
            return counter.getAndIncrement() % size;
        }else {
            return Utils.toPositive(Utils.murmur2(keyBytes)) % size;
        }
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
