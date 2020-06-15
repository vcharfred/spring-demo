package top.vchar.order.feign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.vchar.goods.dto.GoodsDetailDTO;

/**
 * <p> feign 的Sentinel容错类 </p>
 * <p>
 * 容错类要求必须实现被容错的接口,并为每个方法实现容错方案
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/15
 */
@Slf4j
@Component
public class GoodsFeignClientFallBack implements GoodsFeignClient {

    @Override
    public GoodsDetailDTO findGoodsDetailByGoodsNo(String goodsNo) {
        log.error("服务异常");
        return null;
    }
}
