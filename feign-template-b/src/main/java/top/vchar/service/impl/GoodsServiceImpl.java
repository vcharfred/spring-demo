package top.vchar.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import top.vchar.entity.Goods;
import top.vchar.service.GoodsService;

/**
 * <p> 商品接口类实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/5/17
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Override
    public Goods findById(Long id) {

        System.out.println("查询商品ID为：" + id);

        Assert.notNull(id, "商品ID不能为空");
        Assert.isTrue(id.compareTo(1L) == 0, "商品不存在");

        Goods goods = new Goods();
        goods.setId(1L);
        goods.setGoodsName("华为P50 Pro");
        goods.setNum(100);
        return goods;
    }
}
