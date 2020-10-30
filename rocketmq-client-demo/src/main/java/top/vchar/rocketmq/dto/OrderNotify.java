package top.vchar.rocketmq.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p> 订单通知 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/29
 */
@Data
public class OrderNotify implements Serializable {

    @NotNull(message = "订单号不能为空")
    private String orderNo;

    @NotNull(message = "消息内容不能为空")
    private String message;

}
