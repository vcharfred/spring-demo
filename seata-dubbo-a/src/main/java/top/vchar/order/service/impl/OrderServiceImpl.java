package top.vchar.order.service.impl;

import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.vchar.goods.service.GoodsService;
import top.vchar.order.entity.Order;
import top.vchar.order.mapper.OrderMapper;
import top.vchar.order.service.OrderService;

import java.sql.SQLException;

/**
 * <p> 订单业务接口实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/3/27
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @DubboReference(interfaceClass = GoodsService.class, check = false, timeout = 5000)
    private GoodsService goodsService;

    /**
     * 预定
     *
     * @param goodsId 商品id
     * @param num     数量
     * @return 返回订单号
     */
    @GlobalTransactional
    @Override
    public String booking(Long goodsId, Integer num) throws SQLException {

        Order order = new Order();
        order.setOrderNo(String.valueOf(System.currentTimeMillis()));
        order.setUid(1L);
        order.setGoodsId(goodsId);
        order.setIntegral(num * 50);

        int count = orderMapper.insert(order);
        if (count != 1) {
            System.out.println("订单创建失败");
            return "订单创建失败";
        }
        boolean res = this.goodsService.deductInventory(goodsId, num);
        if (!res) {
            throw new SQLException("库存不足");
        }

        return order.getOrderNo();
    }
}
