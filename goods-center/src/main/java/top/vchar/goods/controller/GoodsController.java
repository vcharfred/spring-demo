package top.vchar.goods.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public GoodsDetailDTO findGoodsByGoodsNo(@PathVariable("goodNo") String goodNo){
        log.info("商品编号：{}", goodNo);
        return goodsService.findGoodsByGoodsNo(goodNo);
    }

}
