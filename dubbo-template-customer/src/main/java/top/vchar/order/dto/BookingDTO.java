package top.vchar.order.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <p> 预定参数 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/3/1
 */
@Data
public class BookingDTO implements Serializable {

    /**
     * 商品ID
     */
    private Long goodsId;

    /**
     * 订购数量
     */
    private Integer num;

    /**
     * 联系人名称
     */
    private String contactPerson;
}
