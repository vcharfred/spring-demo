package top.vchar.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p> 商城订单信息 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/17
 */
@Data
public class MallOrderDTO implements Serializable {

    /**
     * 订单号
     */
    private Long orderNo;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 商品编号
     */
    private String goodsNo;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 数量
     */
    private Integer amount;

    /**
     * 价格
     */
    private BigDecimal price;
}
