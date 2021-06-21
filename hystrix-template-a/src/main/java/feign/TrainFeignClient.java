package feign;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * <p> feign客户端 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/6/21
 */
@FeignClient(name = "hystrix-server-b", fallbackFactory = TrainFeignClientFactory.class)
public interface TrainFeignClient {

}
