package top.vchar.goods.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p> 商品信息DTO </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/12
 */
@Data
public class GoodsDetailDTO implements Serializable {

    /**
     * 商品编号
     */
    private String goodsNo;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 库存
     */
    private Integer inventory;

    /**
     * 价格
     */
    private BigDecimal price;

}
