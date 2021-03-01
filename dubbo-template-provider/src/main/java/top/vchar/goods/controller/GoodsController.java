package top.vchar.goods.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.vchar.goods.dto.GoodsDetailDTO;
import top.vchar.goods.service.GoodsService;

/**
 * <p> 商品路由 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/3/1
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping
    public GoodsDetailDTO findById(Long id){
        return this.goodsService.findGoodsById(id);
    }

}
