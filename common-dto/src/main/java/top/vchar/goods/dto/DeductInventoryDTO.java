package top.vchar.goods.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <p> 扣除库存参数 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/17
 */
@Data
public class DeductInventoryDTO implements Serializable {

    /**
     * 商品编号
     */
    private String goodNo;

    /**
     * 数量
     */
    private Integer amount;

}
