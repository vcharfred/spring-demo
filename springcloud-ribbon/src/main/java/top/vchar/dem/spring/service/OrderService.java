package top.vchar.dem.spring.service;

import top.vchar.dem.spring.pojo.ProductOrder;

/**
 * <p> 订单接口 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/8/5 21:40
 */
public interface OrderService {
    ProductOrder save1(int userId, int productId);

    ProductOrder save2(int userId, int productId);
}
