package top.vchar.train.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.vchar.train.dto.TrainStationNameDTO;
import top.vchar.train.entity.TrainStationName;
import top.vchar.train.service.TrainBaseDataService;

import java.time.Duration;
import java.util.List;

/**
 * <p> 火车票基础数据controller </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/21
 */
@RestController
@RequestMapping("/base")
public class TrainBaseDataController {

    private final TrainBaseDataService trainBaseDataService;

    public TrainBaseDataController(TrainBaseDataService trainBaseDataService) {
        this.trainBaseDataService = trainBaseDataService;
    }

    @GetMapping("/station_name/pull_12306")
    public Mono<String> importTrainStationName(){
        return trainBaseDataService.importTrainStationName();
    }

    @GetMapping("/station_name")
    public Flux<TrainStationName> findAllTrainStationName(){
         List<TrainStationName> list = trainBaseDataService.listAll();
        return Flux.fromIterable(list).delayElements(Duration.ofMillis(100));
    }

    @GetMapping("/station_name/search")
    public Flux<TrainStationNameDTO> findTrainStationName(@RequestParam("keywords") String keywords){
//        List<TrainStationNameDTO> list = trainBaseDataService.findTrainStationName(keywords);
//        return Flux.fromIterable(list);

        return trainBaseDataService.findTrainStationName(keywords);
    }



}
