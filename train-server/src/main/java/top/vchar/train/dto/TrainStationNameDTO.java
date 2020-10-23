package top.vchar.train.dto;

import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import top.vchar.train.entity.TrainStationName;

import java.io.Serializable;

/**
 * <p> 火车站点信息DTO </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/22
 */
@Data
@Document(indexName = "train_station_name")
public class TrainStationNameDTO implements Serializable {

    @Id
    private Long id;

    /**
     * 三字码
     */
    @Field(type = FieldType.Keyword)
    private String code;

    /**
     * 拼音
     */
    @Field(type = FieldType.Keyword)
    private String pinyin;

    /**
     * 拼音简写
     */
    @Field(type = FieldType.Keyword)
    private String pinyinShort;

    /**
     * 中文名称
     */
    @Field(type = FieldType.Keyword)
    private String cnName;

    public static TrainStationNameDTO cloneTrainStationName(TrainStationName stationName){
        TrainStationNameDTO dto = new TrainStationNameDTO();
        BeanUtils.copyProperties(stationName, dto);
        return dto;
    }
}
