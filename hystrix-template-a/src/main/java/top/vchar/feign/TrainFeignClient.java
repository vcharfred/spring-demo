package top.vchar.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import top.vchar.entity.Train;

/**
 * <p> feign客户端 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/6/21
 */
@FeignClient(name = "hystrix-server-b", fallbackFactory = TrainFeignClientFactory.class)
public interface TrainFeignClient {

    @GetMapping("/find")
    Train findTrain();
}
