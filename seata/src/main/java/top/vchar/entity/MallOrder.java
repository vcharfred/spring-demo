package top.vchar.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p> 订单信息实体类 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/17
 */
@Data
@TableName("mall_order")
public class MallOrder implements Serializable {

    /**
     * ID信息
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 订单号
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 用户名称
     */
    @TableField("user_name")
    private String userName;

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
     * 数量
     */
    @TableField("amount")
    private Integer amount;

    /**
     * 价格
     */
    @TableField("price")
    private BigDecimal price;

}
