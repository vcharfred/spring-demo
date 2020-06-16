package top.vchar.order.feign;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.vchar.user.dto.UserDetailDTO;

/**
 * <p> feign客户端调用异常处理 </p>
 * <p>
 * 这种实现方式可以将错误日志打印出来
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/15
 */
@Slf4j
@Component
public class UserFeignClientFallBackFactory implements FallbackFactory<UserFeignClient> {

    @Override
    public UserFeignClient create(Throwable throwable) {
        throwable.printStackTrace();
        return new UserFeignClient() {
            @Override
            public UserDetailDTO findUserById(Integer id) {
                // TODO
                return null;
            }
        };
    }
}
