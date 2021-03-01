package top.vchar.order.service;

import top.vchar.order.dto.BookingDTO;

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
     * @param bookingDTO 参数
     * @return 返回预定结果
     */
    String booking(BookingDTO bookingDTO);

}
