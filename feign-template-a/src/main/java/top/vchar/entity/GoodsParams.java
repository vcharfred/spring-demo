package top.vchar.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * <p> 参数 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/5/17
 */
@Data
public class GoodsParams implements Serializable {

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

    public static GoodsParams build() {
        GoodsParams params = new GoodsParams();
        params.setId(1L);
        params.setGoodsName("MacBook Pro");
        params.setNum(100);
        return params;
    }

}
