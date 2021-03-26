package top.vchar.integral.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p> 积分信息 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/3/26
 */
@TableName("integral")
@Data
public class Integral implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long uid;

    /**
     * 剩余积分数
     */
    private Integer point;

}
