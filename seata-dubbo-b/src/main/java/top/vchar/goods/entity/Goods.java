package top.vchar.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p> 商品信息 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/3/27
 */
@TableName(value = "integral_goods")
@Data
public class Goods implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 名字
     */
    @TableField("goods_name")
    private String goodsName;

    /**
     * 积分
     */
    @TableField("integral")
    private Integer integral;

    /**
     * 库存
     */
    @TableField("inventory")
    private Integer inventory;
}
