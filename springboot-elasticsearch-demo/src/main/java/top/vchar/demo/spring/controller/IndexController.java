package top.vchar.demo.spring.controller;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import top.vchar.demo.spring.pojo.TrainStation;
import top.vchar.demo.spring.repository.TrainStationRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>  TODO 功能描述 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/22 23:40
 */
@RestController
public class IndexController {

    @Autowired
    private TrainStationRepository trainStationRepository;

    @PostMapping("/save_train")
    public String esSaveDemo(String stationStr){
        if(null!=stationStr){
            TrainStation trainStation = JSONObject.parseObject(stationStr, TrainStation.class);
            if(trainStation.getId()!=null){
                trainStationRepository.save(trainStation);
                System.out.println("保存成功");
                return "200";
            }
        }
        System.out.println("保持失败");//INSTANCE
        return "401";
    }

    @GetMapping("/search_train")
    public String esSearchDemo(String key){
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("stationName", key);
        List<TrainStation> list = new ArrayList<>();
        Iterable<TrainStation> search = trainStationRepository.search(queryBuilder);
        search.forEach(list::add);
        return JSONObject.toJSONString(list);
    }




}
