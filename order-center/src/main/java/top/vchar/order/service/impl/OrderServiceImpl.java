package top.vchar.order.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import top.vchar.goods.dto.GoodsDetailDTO;
import top.vchar.order.dto.CreateOrderDTO;
import top.vchar.order.dto.OrderDetailDTO;
import top.vchar.order.entity.Order;
import top.vchar.order.feign.GoodsFeignClient;
import top.vchar.order.feign.UserFeignClient;
import top.vchar.order.mapper.OrderMapper;
import top.vchar.order.service.IOrderService;
import top.vchar.user.dto.UserDetailDTO;

import java.math.BigDecimal;

/**
 * <p> 订单业务接口实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/12
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    private final RestTemplate restTemplate;
    private final GoodsFeignClient goodsFeignClient;
    private final UserFeignClient userFeignClient;

    public OrderServiceImpl(RestTemplate restTemplate, GoodsFeignClient goodsFeignClient, UserFeignClient userFeignClient) {
        this.restTemplate = restTemplate;
        this.goodsFeignClient = goodsFeignClient;
        this.userFeignClient = userFeignClient;
    }

    /**
     * 通过订单号查询订单详情
     *
     * @param orderNo 订单号
     * @return 返回订单详情
     */
    @Override
    public OrderDetailDTO findOrderByOrderNo(String orderNo) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getOrderNo, orderNo);
        Order order = this.getOne(queryWrapper, false);
        if(order!=null){
            OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
            BeanUtils.copyProperties(order, orderDetailDTO);
            return orderDetailDTO;
        }
        return null;
    }

    /**
     * 原始restTemplate 的创建订单号
     * @param createOrderDTO 参数信息
     * @return 返回订单号
     */
    @Override
    public String crateOrder(CreateOrderDTO createOrderDTO) {
        GoodsDetailDTO goodsDetailDTO = restTemplate.getForObject("http://127.0.0.1:8093/goods/detail/"+createOrderDTO.getGoodsNo(), GoodsDetailDTO.class);
        UserDetailDTO userDetailDTO = restTemplate.getForObject("http://127.0.0.1:8091/user/detail/"+createOrderDTO.getUserId(), UserDetailDTO.class);
        return saveOrder(goodsDetailDTO, userDetailDTO, createOrderDTO);
    }

    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 通过DiscoveryClient获取服务地址信息
     * @param createOrderDTO 参数信息
     * @return 返回订单号
     */
    @Override
    public String crateOrderDiscoveryClient(CreateOrderDTO createOrderDTO){
        // discoveryClient.getInstances 获取的是个列表，因此可以基于此自定义访问那台服务器，实现自定义负载均衡
        ServiceInstance goodsServer = discoveryClient.getInstances("goods-server").get(0);
        GoodsDetailDTO goodsDetailDTO = restTemplate.getForObject("http://"+goodsServer.getHost()+":"+goodsServer.getPort()+"/goods/detail/"+createOrderDTO.getGoodsNo(), GoodsDetailDTO.class);

        ServiceInstance userServer = discoveryClient.getInstances("user-server").get(0);
        UserDetailDTO userDetailDTO = restTemplate.getForObject("http://"+userServer.getHost()+":"+userServer.getPort()+"/user/detail/"+createOrderDTO.getUserId(), UserDetailDTO.class);

        return saveOrder(goodsDetailDTO, userDetailDTO, createOrderDTO);
    }

    /**
     * 在restTemplate bean注入的地方添加 @LoadBalanced注解；使其使用ribbon来实现负载均衡
     *
     * @param createOrderDTO 参数信息
     * @return 返回订单号
     */
    @Override
    public String crateOrderLoadBalanced(CreateOrderDTO createOrderDTO) {
        // 在restTemplate bean注入的地方添加 @LoadBalanced注解；使其使用ribbon来实现负载均衡
        GoodsDetailDTO goodsDetailDTO = restTemplate.getForObject("http://goods-server/goods/detail/" + createOrderDTO.getGoodsNo(), GoodsDetailDTO.class);
        UserDetailDTO userDetailDTO = restTemplate.getForObject("http://user-server/user/detail/" + createOrderDTO.getUserId(), UserDetailDTO.class);
        return saveOrder(goodsDetailDTO, userDetailDTO, createOrderDTO);
    }

    /**
     * 通过feign做服务调用和ribbon负载均衡
     *
     * @param createOrderDTO 参数信息
     * @return 返回订单号
     */
    @Override
    public String crateOrderFeign(CreateOrderDTO createOrderDTO) {
        // 通过feign做服务调用和ribbon负载均衡
        GoodsDetailDTO goodsDetailDTO = goodsFeignClient.findGoodsDetailByGoodsNo(createOrderDTO.getGoodsNo());
        UserDetailDTO userDetailDTO = userFeignClient.findUserById(createOrderDTO.getUserId());
        return saveOrder(goodsDetailDTO, userDetailDTO, createOrderDTO);
    }

    /**
     * 创建订单号
     *
     * @param createOrderDTO 参数信息
     * @return 返回订单号
     */
    private String saveOrder(GoodsDetailDTO goodsDetailDTO, UserDetailDTO userDetailDTO, CreateOrderDTO createOrderDTO) {
        Assert.notNull(goodsDetailDTO, "无此商品信息");
        Assert.notNull(userDetailDTO, "用户信息不存在");
        Order order = new Order();
        order.setOrderNo("EC" + System.currentTimeMillis());
        order.setGoodsNo(createOrderDTO.getGoodsNo());
        order.setGoodsName(goodsDetailDTO.getGoodsName());
        order.setPrice(goodsDetailDTO.getPrice().multiply(new BigDecimal(createOrderDTO.getAmount().toString())));
        order.setUserId(userDetailDTO.getId());
        order.setUserName(userDetailDTO.getUserName());
        order.setAmount(createOrderDTO.getAmount());
        this.save(order);
        return order.getOrderNo();
    }

    /**
     * 查询订单信息 @SentinelResource注解实现熔断
     *
     * <p>
     * blockHandler: 当内部发生BlockException异常时触发
     * fallback：     当内部发生Throwable异常时触发
     * </p>
     *
     * @param orderNo 订单编号
     * @return 返回订单信息
     */
    @SentinelResource(value = "order-detail", blockHandler = "blockHandler", fallbackClass = SentinelResourceDemoFallback.class)
    @Override
    public OrderDetailDTO sentinelResourceDemo(String orderNo) {
        OrderDetailDTO orderDetail = findOrderByOrderNo(orderNo);
        Assert.notNull(orderDetail, "无此订单信息");
        return orderDetail;
    }

    /**
     * 发生BlockException异常时触发，注意返回值和参数必须和原理的相同，这个和feign的差不多
     */
    public OrderDetailDTO blockHandler(String orderNo, BlockException e) {
        log.error("触发降级限流异常", e);
        return null;
    }

    /**
     * 发生Throwable异常时触发，注意返回值和参数必须和原理的相同，这个和feign的差不多
     */
    @Slf4j
    public static class SentinelResourceDemoFallback {
        // 这个方法必须使用 static 修饰方法
        public static OrderDetailDTO fallback(String orderNo, Throwable throwable) {
            log.error("无此订单信息", throwable);
            return null;
        }
    }

}
