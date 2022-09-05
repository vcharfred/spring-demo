package top.vchar.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <p> 使用Feign做网络请求工具 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2022/8/12
 */
@FeignClient(name = "httpFeignClient", url = "https://baidu.com")
public interface HttpFeignClient {

    @GetMapping("/mz.html")
    String findPage();

}
