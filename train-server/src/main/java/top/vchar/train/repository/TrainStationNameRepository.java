package top.vchar.train.repository;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;
import top.vchar.train.dto.TrainStationNameDTO;

import java.util.List;

/**
 * <p> 火车站点信息Repository </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/22
 */
@Component
@Document(indexName = "train_station_name")
public interface TrainStationNameRepository extends ElasticsearchRepository<TrainStationNameDTO, Long> {

    /**
     * 查询以keywords开头的站点信息
     * @param cnName 中文
     * @param pinyin 拼音
     * @return 返回结果
     */
    List<TrainStationNameDTO> findByCnNameStartingWithOrPinyinStartingWith(String cnName, String pinyin);

}
