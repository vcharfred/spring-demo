package top.vchar.order.service;

import java.sql.SQLException;

/**
 * <p> 订单业务接口 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/3/27
 */
public interface OrderService {

    /**
     * 预定
     *
     * @param goodsId 商品id
     * @param num     数量
     * @return 返回订单号
     */
    String booking(Long goodsId, Integer num) throws SQLException;

}
