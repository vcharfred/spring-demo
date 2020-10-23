package top.vchar.train.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchEntityInformation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.vchar.common.exception.BizException;
import top.vchar.train.dto.TrainStationNameDTO;
import top.vchar.train.entity.TrainStationName;
import top.vchar.train.mapper.TrainStationNameMapper;
import top.vchar.train.repository.TrainStationNameRepository;
import top.vchar.train.service.TrainBaseDataService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p> 基础数据业务接口实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/21
 */
@Slf4j
@Service
public class TrainBaseDataServiceImpl implements TrainBaseDataService {

    private final TrainStationNameMapper trainStationNameMapper;
    @Autowired
    private TrainStationNameRepository trainStationNameRepository;

    /**
     * 12306站点名称获取地址
     */
    private static final String STATION_NAME_URL = "https://www.12306.cn/index/script/core/common/station_name_v10095.js";

    public TrainBaseDataServiceImpl(TrainStationNameMapper trainStationNameMapper) {
        this.trainStationNameMapper = trainStationNameMapper;
    }

    /**
     * 导入火车票站点信息
     * @return 返回导入结果
     */
    @Override
    public Mono<String> importTrainStationName() {
        return WebClient.create(STATION_NAME_URL).get().retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse-> Mono.error(new BizException("数据拉取失败")))
                .bodyToMono(String.class)
                .flatMap(body->{
                    List<TrainStationName> list = extractStationName(body);
                    return Mono.just(batchInsert(list));
                });
    }

    /**
     * 查询所有的站点信息
     * @return 返回所有的站点信息
     */
    @Override
    public List<TrainStationName> listAll() {
        LambdaQueryWrapper<TrainStationName> queryWrapper = new LambdaQueryWrapper<>();
        List<TrainStationName> list = trainStationNameMapper.selectList(queryWrapper);
        return list==null? Lists.newArrayList():list;
    }

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 通过关键词查询火车站点信息
     * @param keywords 关键词
     * @return 返回查询结果
     */
    @Override
    public Flux<TrainStationNameDTO> findTrainStationName(String keywords) {

///     return trainStationNameRepository.findByCnNameStartingWithOrPinyinStartingWith(keywords, keywords);

        BoolQueryBuilder boolQuery = new BoolQueryBuilder();
        QueryBuilder cnNameQuery = new PrefixQueryBuilder("cnName.keyword", keywords);
        boolQuery.should(cnNameQuery);
        QueryBuilder pinyinQuery = new MatchPhrasePrefixQueryBuilder("pinyin", keywords);
        boolQuery.should(pinyinQuery);
        Query query = new NativeSearchQuery(boolQuery);
///     return elasticsearchRestTemplate.search(query, TrainStationNameDTO.class).map(SearchHit::getContent);
        return Flux.fromIterable(elasticsearchRestTemplate.search(query, TrainStationNameDTO.class)).map(SearchHit::getContent);
    }

    private List<TrainStationName> extractStationName(String str){
        String[] arr = str.split("\\|");
        List<TrainStationName> list = new ArrayList<>();
        for(int i=1; i<arr.length; i++){
            TrainStationName stationName = new TrainStationName();
            stationName.setCnName(arr[i++]);
            stationName.setCode(arr[i++]);
            stationName.setPinyin(arr[i++]);
            stationName.setPinyinShort(arr[i++]);
            list.add(stationName);
        }
        return list;
    }

    private String batchInsert(List<TrainStationName> list){
        int add = 0;
        int totalPull = list.size();
        for(TrainStationName stationName:list){
            LambdaQueryWrapper<TrainStationName> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TrainStationName::getCnName, stationName.getCnName());
            TrainStationName stationNameDb = trainStationNameMapper.selectOne(queryWrapper);
            if(stationNameDb==null){
                add++;
                trainStationNameMapper.insert(stationName);
                stationNameDb = stationName;
            }

            if(stationNameDb.getId()!=null){
                boolean exists = trainStationNameRepository.existsById(stationNameDb.getId());
                if(!exists){
                    trainStationNameRepository.save(TrainStationNameDTO.cloneTrainStationName(stationNameDb));
                }
            }
        }
        return String.format("共计拉取:%d条数据，新增：%d条数据", totalPull, add);
    }

}
