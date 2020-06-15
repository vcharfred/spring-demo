package top.vchar.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.vchar.goods.dto.GoodsDetailDTO;

/**
 * <p> 商品服务 feign客户端 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/15
 */
@FeignClient(value = "goods-server", fallback = GoodsFeignClientFallBack.class)
public interface GoodsFeignClient {

    /**
     * 通过商品编号获取商品
     *
     * @param goodsNo 商品编号
     * @return 返回结果
     */
    @GetMapping("/goods/detail/{goodsNo}")
    GoodsDetailDTO findGoodsDetailByGoodsNo(@PathVariable("goodsNo") String goodsNo);

}
