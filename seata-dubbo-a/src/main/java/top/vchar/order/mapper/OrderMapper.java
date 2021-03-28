package top.vchar.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.vchar.order.entity.Order;

/**
 * <p> 订单mapper </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/3/27
 */
@Mapper
@Repository
public interface OrderMapper extends BaseMapper<Order> {


}
