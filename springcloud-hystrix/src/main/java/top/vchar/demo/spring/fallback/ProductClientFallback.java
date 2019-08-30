package top.vchar.demo.spring.fallback;

import org.springframework.stereotype.Component;
import top.vchar.demo.spring.service.ProductClient;

/**
 * <p>  TODO 功能描述 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/8/30 22:26
 */
@Component
public class ProductClientFallback implements ProductClient {


    @Override
    public String findProductById(int id) {
        System.out.println("Feign 调用服务异常");
        return null;
    }
}
