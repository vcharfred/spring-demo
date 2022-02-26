package top.vchar.feign;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import top.vchar.entity.Train;

/**
 * <p>  TODO 功能描述 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/6/21
 */
@Component
public class TrainFeignClientFactory implements FallbackFactory<TrainFeignClient> {

    @Override
    public TrainFeignClient create(Throwable cause) {
        cause.printStackTrace();
        return new TrainFeignClient() {
            @Override
            public Train findTrain() {
                System.out.println("服务异常....");
                return null;
            }
        };
    }
}
