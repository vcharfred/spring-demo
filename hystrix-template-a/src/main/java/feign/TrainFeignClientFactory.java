package feign;

import feign.hystrix.FallbackFactory;

/**
 * <p>  TODO 功能描述 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/6/21
 */
public class TrainFeignClientFactory implements FallbackFactory<TrainFeignClient> {

    @Override
    public TrainFeignClient create(Throwable cause) {
        cause.printStackTrace();
        return new TrainFeignClient() {
        };
    }
}
