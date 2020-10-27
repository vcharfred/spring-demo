package top.vchar.pay.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.vchar.pay.entity.PayOrder;

/**
 * <p> 支付订单mapper </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/27
 */
@Mapper
@Repository
public interface PayOrderMapper extends BaseMapper<PayOrder> {


}
