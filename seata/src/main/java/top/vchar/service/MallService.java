package top.vchar.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.vchar.dto.MallOrderDTO;
import top.vchar.entity.MallOrder;

/**
 * <p> 商城业务接口 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/17
 */
public interface MallService extends IService<MallOrder> {

    /**
     * 下单
     *
     * @param goodsNo 商品ID
     * @return 返回下单结果
     */
    String booking(String goodsNo);

    /**
     * 查询商城订单信息
     *
     * @param orderNo 订单号
     * @return 返回商城订单信息
     */
    MallOrderDTO getOrder(String orderNo);
}
