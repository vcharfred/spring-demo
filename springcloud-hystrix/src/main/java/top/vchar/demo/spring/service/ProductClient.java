package top.vchar.demo.spring.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.vchar.demo.spring.fallback.ProductClientFallback;

/**
 * <p> 商品服务客户端 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/8/26 22:58
 */
@FeignClient(name = "product-service", fallback = ProductClientFallback.class)
public interface ProductClient {

    @GetMapping("/api/v1/product/find")
    String findProductById(@RequestParam(value = "id") int id);

}
