package top.vchar.demo.spring.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p> 酒店信息 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/10/29
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Hotel implements Serializable {

    /**
     * 酒店ID
     */
    private Integer id;

    /**
     * 酒店名称
     */
    private String hotelName;

    /**
     * 房间数
     */
    private Integer roomNum;

    /**
     * 价格
     */
    private BigDecimal price;

}
