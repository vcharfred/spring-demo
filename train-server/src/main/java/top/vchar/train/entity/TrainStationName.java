package top.vchar.train.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p> 火车站点信息 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/21
 */
@Data
@TableName("train_station_name")
public class TrainStationName implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 三字码
     */
    @TableField("code")
    private String code;

    /**
     * 拼音
     */
    @TableField("pinyin")
    private String pinyin;

    /**
     * 拼音简写
     */
    @TableField("pinyin_short")
    private String pinyinShort;

    /**
     * 中文名称
     */
    @TableField("cn_name")
    private String cnName;

}
