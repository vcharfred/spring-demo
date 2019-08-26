package top.vchar.dem.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.vchar.dem.spring.pojo.ProductOrder;
import top.vchar.dem.spring.service.OrderService;

/**
 * <p> 订单路由 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/8/5 21:39
 */
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/save1")
    public ProductOrder save1(@RequestParam("user_id") int userId, @RequestParam("product_id") int productId){
        return orderService.save1(userId, productId);
    }

    @RequestMapping("/save2")
    public ProductOrder save2(@RequestParam("user_id") int userId, @RequestParam("product_id") int productId){
        return orderService.save2(userId, productId);
    }

}
