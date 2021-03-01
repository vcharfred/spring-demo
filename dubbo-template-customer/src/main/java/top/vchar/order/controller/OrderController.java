package top.vchar.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.vchar.order.dto.BookingDTO;
import top.vchar.order.service.OrderService;

/**
 * <p> 订单路由 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/3/1
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String booking(BookingDTO bookingDTO){
        return this.orderService.booking(bookingDTO);
    }
}
