package top.vchar.order.controller;

import org.springframework.web.bind.annotation.*;
import top.vchar.order.dto.CreateOrderDTO;
import top.vchar.order.dto.OrderDetailDTO;
import top.vchar.order.service.IOrderService;

import javax.validation.Valid;

/**
 * <p> 订单控制器 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/12
 */
@RequestMapping("/order")
@RestController
public class OrderController {

    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * 通过订单号查询订单详情
     * @param orderNo 订单号
     * @return 返回订单详情
     */
    @GetMapping("/detail/{orderNo}")
    public OrderDetailDTO findOrder(@PathVariable("orderNo") String orderNo){
        return orderService.findOrderByOrderNo(orderNo);
    }

    /**
     * 创建订单号
     * @param createOrderDTO 参数信息
     * @return 返回订单号
     */
    @PostMapping("/create/{type}")
    public String crateOrder(@PathVariable("type") int type, @Valid @RequestBody CreateOrderDTO createOrderDTO){
        if(1==type){
            // 直接写死服务地址
            return orderService.crateOrder(createOrderDTO);
        }else if(2==type){
            // 通过DiscoveryClient获取服务信息
            return orderService.crateOrderDiscoveryClient(createOrderDTO);
        }else if(3==type){
            // 在restTemplate bean注入的地方添加 @LoadBalanced注解；使其使用ribbon来实现负载均衡
            return orderService.crateOrderLoadBalanced(createOrderDTO);
        }
        return null;
    }

}
