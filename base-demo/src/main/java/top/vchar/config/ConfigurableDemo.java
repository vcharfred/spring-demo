package top.vchar.config;

import org.springframework.stereotype.Component;
import top.vchar.service.OrderService;

/**
 * <p> @Configurable 示例 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/9/3
 */
@Component
public class ConfigurableDemo {

    public void buy(){
        OrderService orderService = new OrderService();
        orderService.booking();
        System.out.println("执行成功");
    }

}
