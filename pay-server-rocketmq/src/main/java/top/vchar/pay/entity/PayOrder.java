package top.vchar.pay.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p> 支付订单信息 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/27
 */
@Data
@TableName("pay_order")
public class PayOrder implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单号
     */
    @TableField(value = "order_no")
    private String orderNo;

    /**
     * 金额
     */
    @TableField(value = "amount")
    private BigDecimal amount;

    /**
     * 创建
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 通知次数
     */
    @TableField(value = "notify")
    private Integer notify;

    /**
     * 是否通知成功
     */
    @TableField(value = "notify_ok")
    private Integer notifyOk;
}
