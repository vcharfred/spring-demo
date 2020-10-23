package top.vchar.train.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.vchar.train.dto.TrainStationNameDTO;
import top.vchar.train.entity.TrainStationName;

import java.util.List;

/**
 * <p> 基础数据业务接口 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/21
 */
public interface TrainBaseDataService {

    /**
     * 导入火车票站点信息
     * @return 返回导入结果
     */
    Mono<String> importTrainStationName();

    /**
     * 查询所有的站点信息
     * @return 返回所有的站点信息
     */
    List<TrainStationName> listAll();

    /**
     * 通过关键词查询火车站点信息
     * @param keywords 关键词
     * @return 返回查询结果
     */
    Flux<TrainStationNameDTO> findTrainStationName(String keywords);
}
