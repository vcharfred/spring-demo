package top.vchar.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import top.vchar.goods.dto.DeductInventoryDTO;
import top.vchar.goods.dto.GoodsDetailDTO;

/**
 * <p> 商品feign </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/17
 */
@FeignClient(value = "goods-server")
public interface GoodsFeignClient {

    /**
     * 通过商品编号获取商品
     *
     * @param goodsNo 商品编号
     * @return 返回结果
     */
    @GetMapping("/goods/detail/{goodsNo}")
    GoodsDetailDTO findGoodsDetailByGoodsNo(@PathVariable("goodsNo") String goodsNo);

    /**
     * 扣除库存
     *
     * @param deductInventoryDTO 参数
     * @return 返回结果
     */
    @PostMapping("/goods/deduct")
    Boolean deductInventory(@Validated @RequestBody DeductInventoryDTO deductInventoryDTO);

}
