package top.vchar.demo.spring.repository;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;
import top.vchar.demo.spring.pojo.TrainStation;

/**
 * <p>  TODO 功能描述 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/22 23:47
 */
@Component
@Document(indexName = "train", type = "station", shards = 1, replicas = 0)
public interface TrainStationRepository extends ElasticsearchRepository<TrainStation, String> {
}
