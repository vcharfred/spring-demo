package top.vchar.order.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.vchar.order.dao.OrderDao;
import top.vchar.order.entity.Order;
import top.vchar.order.mapper.OrderMapper;

/**
 * <p> 订单dao实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/3/1
 */
@Service
public class OrderDaoImpl extends ServiceImpl<OrderMapper, Order> implements OrderDao {

}
