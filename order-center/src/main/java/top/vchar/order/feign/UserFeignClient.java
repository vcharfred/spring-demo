package top.vchar.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.vchar.user.dto.UserDetailDTO;

/**
 * <p> 用户feign客户端实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/15
 */
@FeignClient(value = "user-server", fallbackFactory = UserFeignClientFallBackFactory.class)
public interface UserFeignClient {

    /**
     * 查询用户信息
     *
     * @param id 用户ID
     * @return 返回结果
     */
    @GetMapping("/user/detail/{id}")
    UserDetailDTO findUserById(@PathVariable("id") Integer id);

}
