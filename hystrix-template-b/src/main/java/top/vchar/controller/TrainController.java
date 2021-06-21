package top.vchar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.vchar.entity.Train;
import top.vchar.service.TrainService;

/**
 * <p> 火车票路由 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/6/21
 */
@RestController
public class TrainController {

    @Autowired
    private TrainService trainService;

    @GetMapping("/find")
    public Train findTrain() {
        return this.trainService.findTrain();
    }

}
