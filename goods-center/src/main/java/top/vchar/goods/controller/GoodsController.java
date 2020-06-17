package top.vchar.goods.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.vchar.goods.dto.DeductInventoryDTO;
import top.vchar.goods.dto.GoodsDetailDTO;
import top.vchar.goods.service.IGoodsService;

/**
 * <p> 商品控制器 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/12
 */
@Slf4j
@RestController
@RequestMapping("/goods")
public class GoodsController {

    private final IGoodsService goodsService;

    public GoodsController(IGoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @GetMapping("/detail/{goodNo}")
    public GoodsDetailDTO findGoodsByGoodsNo(@PathVariable("goodNo") String goodNo) {
        log.info("商品编号：{}", goodNo);
        return goodsService.findGoodsByGoodsNo(goodNo);
    }

    /**
     * 扣除库存
     *
     * @param deductInventoryDTO 参数
     * @return 返回结果
     */
    @PostMapping("/goods/deduct")
    public Boolean deductInventory(@Validated @RequestBody DeductInventoryDTO deductInventoryDTO) {
        log.info("扣除库存：{}", deductInventoryDTO);
        return goodsService.deductInventory(deductInventoryDTO);
    }

}
