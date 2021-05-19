package top.vchar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.vchar.entity.Goods;
import top.vchar.service.GoodsService;

/**
 * <p> 商品路由 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/5/17
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping
    public Goods findById(@RequestHeader(name = "uid") String uid, Long id) {
        System.out.println("请求用户为：" + uid);
        return this.goodsService.findById(id);
    }

}
