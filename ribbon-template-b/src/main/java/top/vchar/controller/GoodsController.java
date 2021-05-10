package top.vchar.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.vchar.entity.Goods;

/**
 * <p> 商品 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/5/10
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Value("${server.port}")
    private Integer port;

    @GetMapping
    public Goods findGoods(Long id) {
        System.out.println("调用：" + port + ", ID=" + id);

        Assert.isTrue(id == 1L, "商品不存在");

        Goods goods = new Goods();
        goods.setId(1L);
        goods.setGoodsName("华为P50 Pro");
        goods.setNum(1000);
        return goods;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
