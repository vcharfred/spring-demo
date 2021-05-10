package top.vchar.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * <p> 商品信息 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/5/10
 */
@Data
public class Goods implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 数量
     */
    private int num;

}
