package top.vchar.hystrix;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.vchar.entity.Train;
import top.vchar.feign.TrainFeignClient;

/**
 * <p> 线程池资源隔离示例1：返回一条数据 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/12/6
 */
@Component
public class HystrixCommandDemo extends HystrixCommand<String> {

    @Autowired
    private TrainFeignClient trainFeignClient;

    protected HystrixCommandDemo() {
        super(HystrixCommandGroupKey.Factory
                .asKey("group_name")
                .wait(2000));
    }

    @Override
    protected String run() throws Exception {
        System.out.println("执行方法");
        Train train = trainFeignClient.findTrain();
        return JSONObject.toJSONString(train);
    }
}
