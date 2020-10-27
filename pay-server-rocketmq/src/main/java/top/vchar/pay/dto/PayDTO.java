package top.vchar.pay.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p> 支付信息 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/27
 */
@Data
public class PayDTO implements Serializable {

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 订单号
     */
    private String orderNo;

}
