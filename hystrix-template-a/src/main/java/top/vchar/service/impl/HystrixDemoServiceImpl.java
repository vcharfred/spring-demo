package top.vchar.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.vchar.entity.Train;
import top.vchar.feign.TrainFeignClient;
import top.vchar.service.HystrixDemoService;

/**
 * <p> hystrix示例业务接口实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/6/22
 */
@Service
public class HystrixDemoServiceImpl implements HystrixDemoService {

    @Autowired
    private TrainFeignClient trainFeignClient;


    @Override
    public Train hystrixMethodDemo1() {
        System.out.println("hystrixMethodDemo1 调用feign服务：trainFeignClient.findTrain()");
        return this.trainFeignClient.findTrain();
    }

    @HystrixCommand(fallbackMethod = "hystrixMethodDemo2Fail")
    @Override
    public Train hystrixMethodDemo2() {
        System.out.println("hystrixMethodDemo2 调用feign服务：trainFeignClient.findTrain()");
        return this.trainFeignClient.findTrain();
    }

    private Train hystrixMethodDemo2Fail() {
        System.out.println("hystrixMethodDemo2Fail ...");
        return null;
    }

    @Override
    public Train hystrixMethodDemo3() {
        // 因为hystrix是通过AOP来实现的；因此一个类的方法间调用时不会生效的。
        return this.hystrixMethodDemo2();
    }

    @Override
    public Train hystrixMethodDemo4() {
        System.out.println("hystrixMethodDemo4 调用feign服务：trainFeignClient.findTrain()");
        return this.trainFeignClient.findTrain();
    }

}
