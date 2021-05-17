package top.vchar.controller;

import org.springframework.web.bind.annotation.*;
import top.vchar.entity.Goods;
import top.vchar.entity.GoodsParams;

/**
 * <p> feign 参数示例 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/5/17
 */
@RestController
@RequestMapping("/goods")
public class FeignParamsController {

    /**
     * GET 请求示例：只有一个参数
     */
    @GetMapping("/demo1")
    public Goods findById(Long id) {
        System.out.println("参数示例1：" + id);
        return buildGoods();
    }

    /**
     * GET 请求示例：多个参数
     */
    @GetMapping("/demo2")
    public Goods findById(Long id, String goodsName) {
        System.out.println("参数示例2：" + id + " " + goodsName);
        return buildGoods();
    }

    /**
     * GET 请求示例：参数是一个对象
     */
    @GetMapping("/demo3")
    public Goods findById(GoodsParams params) {
        System.out.println("参数示例3：" + params);
        return buildGoods();
    }

    /**
     * GET 请求示例：参数是一个对象
     */
    @GetMapping("/demo4")
    public Goods findById(GoodsParams params, Long id) {
        System.out.println("参数示例4：" + params + " " + id);
        return buildGoods();
    }

    /**
     * GET 请求示例：参数是一个对象
     */
    @GetMapping("/demo5/{id}")
    public Goods findGoods(@PathVariable(value = "id") Long id) {
        System.out.println("参数示例5：" + id);
        return buildGoods();
    }

    @PostMapping("/demo6")
    public String checkGoods(@RequestBody GoodsParams params) {
        System.out.println("参数示例6：" + params);
        return "ok";
    }

    private Goods buildGoods() {
        Goods goods = new Goods();
        goods.setId(1L);
        goods.setGoodsName("华为P50 Pro");
        goods.setNum(100);
        return goods;
    }
}
