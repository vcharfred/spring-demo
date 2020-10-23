package top.vchar.train.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.vchar.train.entity.TrainStationName;

/**
 * <p> 火车站点信息mapper </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/21
 */
@Mapper
@Repository
public interface TrainStationNameMapper extends BaseMapper<TrainStationName> {

}
