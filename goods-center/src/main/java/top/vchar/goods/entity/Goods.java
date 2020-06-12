package top.vchar.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p> 商品信息 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/12
 */
@TableName("goods")
@Data
public class Goods implements Serializable {

    /**
     * ID值
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 商品编号
     */
    @TableField("goods_no")
    private String goodsNo;

    /**
     * 商品名称
     */
    @TableField("goods_name")
    private String goodsName;

    /**
     * 库存
     */
    @TableField("inventory")
    private Integer inventory;

    /**
     * 价格
     */
    @TableField("price")
    private BigDecimal price;

}
