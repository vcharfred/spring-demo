package top.vchar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.vchar.feign.TrainFeignClient;
import top.vchar.hystrix.TrainHystrixCommand;
import top.vchar.service.HystrixSourceDemoService;

/**
 * <p> 资源隔离示例 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2022/2/16
 */
@Service
public class HystrixSourceDemoServiceImpl implements HystrixSourceDemoService {

    @Autowired
    private TrainFeignClient trainFeignClient;

    @Override
    public String findOne1() {
        return new TrainHystrixCommand(trainFeignClient).execute();
    }
}
