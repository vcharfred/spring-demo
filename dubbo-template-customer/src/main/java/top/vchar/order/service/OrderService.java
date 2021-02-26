package top.vchar.order.service;

/**
 * <p> 订单服务 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/2/26
 */
public interface OrderService {

    /**
     * 预定
     * @param id 商品ID
     * @param num 数量
     * @return 返回预定结果
     */
    String booking(Long id, int num);

}
