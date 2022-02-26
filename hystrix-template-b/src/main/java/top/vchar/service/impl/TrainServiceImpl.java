package top.vchar.service.impl;

import org.springframework.stereotype.Service;
import top.vchar.entity.Train;
import top.vchar.service.TrainService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p> 火车票查询业务接口实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/6/20
 */
@Service
public class TrainServiceImpl implements TrainService {

    @Override
    public Train findTrain() {
        Train train = new Train();
        train.setDepTime(LocalDateTime.of(2021, 6, 22, 8, 20));
        train.setArrTime(LocalDateTime.of(2021, 6, 22, 10, 20));
        train.setDep("上海");
        train.setArr("北京");
        train.setPrice(new BigDecimal("300"));
        train.setNum(100);
        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return train;
    }


}
