package top.vchar.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.vchar.order.dto.CreateOrderDTO;
import top.vchar.order.dto.OrderDetailDTO;
import top.vchar.order.entity.Order;

/**
 * <p> 订单业务接口 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/12
 */
public interface IOrderService extends IService<Order> {

    /**
     * 通过订单号查询订单详情
     * @param orderNo 订单号
     * @return 返回订单详情
     */
    OrderDetailDTO findOrderByOrderNo(String orderNo);

    /**
     * 创建订单号
     * @param createOrderDTO 参数信息
     * @return 返回订单号
     */
    String crateOrder(CreateOrderDTO createOrderDTO);

    /**
     * 通过DiscoveryClient获取服务地址信息
     * @param createOrderDTO 参数信息
     * @return 返回订单号
     */
    String crateOrderDiscoveryClient(CreateOrderDTO createOrderDTO);

    /**
     * 在restTemplate bean注入的地方添加 @LoadBalanced注解；使其使用ribbon来实现负载均衡
     *
     * @param createOrderDTO 参数信息
     * @return 返回订单号
     */
    String crateOrderLoadBalanced(CreateOrderDTO createOrderDTO);
}
