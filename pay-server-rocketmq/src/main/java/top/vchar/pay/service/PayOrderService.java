package top.vchar.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import reactor.core.publisher.Mono;
import top.vchar.pay.dto.PayDTO;
import top.vchar.pay.entity.PayOrder;

/**
 * <p> 订单业务接口 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/27
 */
public interface PayOrderService extends IService<PayOrder> {

    /**
     * 支付成功订单处理
     * @param payDTO 支付订单信息
     * @return 返回处理结果
     */
    Mono<String> pay(PayDTO payDTO);

    /**
     * 保存支付成功订单
     * @param payDTO 支付订单信息
     * @return 返回处理结果
     */
    boolean savePayOrder(PayDTO payDTO);
}
