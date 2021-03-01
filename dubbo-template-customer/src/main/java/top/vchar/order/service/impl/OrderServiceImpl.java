package top.vchar.order.service.impl;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.vchar.goods.dto.GoodsDetailDTO;
import top.vchar.goods.service.GoodsService;
import top.vchar.order.dao.OrderDao;
import top.vchar.order.dto.BookingDTO;
import top.vchar.order.entity.Order;
import top.vchar.order.service.OrderService;

import java.math.BigDecimal;

/**
 * <p> 订单服务实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/2/26
 */
@Service
public class OrderServiceImpl implements OrderService {

    @DubboReference(interfaceClass = GoodsService.class, check = false)
    private GoodsService goodsService;

    @Autowired
    private OrderDao orderDao;

    /**
     * 预定
     * @param bookingDTO 参数
     * @return 返回预定结果
     */
    @Override
    public String booking(BookingDTO bookingDTO) {
        GoodsDetailDTO goods = this.goodsService.findGoodsById(bookingDTO.getGoodsId());
        if(null==goods){
            return "商品不存在";
        }
        if(goods.getInventory()<bookingDTO.getNum()){
            return "库存不足，当前库存："+goods.getInventory();
        }

        // 扣除库存
        boolean res = goodsService.deductInventory(bookingDTO.getGoodsId(), bookingDTO.getNum());
        if(!res){
            return "库存不足";
        }

        // 保存订单信息
        Order order = new Order();
        BeanUtils.copyProperties(goods, order);
        order.setOrderNo("NO"+System.currentTimeMillis());
        order.setAmount(bookingDTO.getNum());
        order.setPrice(goods.getPrice().multiply(new BigDecimal(bookingDTO.getNum().toString())));
        order.setUserName(bookingDTO.getContactPerson());
        boolean save = this.orderDao.save(order);
        if(save){
            if(bookingDTO.getNum()>2){
                throw new NullPointerException("异常事务测试");
            }
            return order.getOrderNo();
        }
        return "预定成功";
    }
}
