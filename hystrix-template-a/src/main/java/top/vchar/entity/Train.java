package top.vchar.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p> 火车票信息 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/6/20
 */
@Data
public class Train implements Serializable {

    /**
     * 出发时间
     */
    private LocalDateTime depTime;

    /**
     * 到达时间
     */
    private LocalDateTime arrTime;

    /**
     * 出发地
     */
    private String dep;

    /**
     * 到达地
     */
    private String arr;

    /**
     * 余票
     */
    private Integer num;

    /**
     * 价格
     */
    private BigDecimal price;

}
