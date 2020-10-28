package top.vchar.rocketmq.dto;

import lombok.Data;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p> 订单信息 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/28
 */
@Data
public class OrderDTO implements Serializable {

    /**
     * 业务订单号
     */
    @NotNull(message = "业务订单号不能为空")
    private String orderNo;

    /**
     * 交易订单号
     */
    @NotNull(message = "交易订单号不能为空")
    private String tradeNo;

    /**
     * 金额
     */
    @NotNull(message = "金额不能为空")
    private BigDecimal amount;

    /**
     * 交易状态
     */
    @NotNull(message = "交易状态不能为空")
    private String status;

}
