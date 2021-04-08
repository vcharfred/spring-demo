package top.vchar.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.vchar.order.service.OrderService;

import java.sql.SQLException;

/**
 * <p> 订单路由 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/3/27
 */
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/booking")
    public String booking() throws SQLException {
        return this.orderService.booking(1L, 2);
    }
}
