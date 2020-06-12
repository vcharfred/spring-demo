package top.vchar.order.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p> 创建订单参数 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/12
 */
@Data
public class CreateOrderDTO implements Serializable {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Integer userId;

    /**
     * 商品编号
     */
    @NotBlank(message = "商品编号不能为空")
    private String goodsNo;

    /**
     * 数量
     */
    @NotNull(message = "商品数量不能为空")
    private Integer amount;

}
