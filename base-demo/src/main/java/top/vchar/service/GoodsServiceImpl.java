package top.vchar.service;

import org.springframework.stereotype.Service;
import top.vchar.entity.Goods;

import java.math.BigDecimal;

/**
 * <p> 商品服务实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/9/3
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    private static Goods goods = new Goods();
    static {
        goods.setId(1L);
        goods.setGoodName("商品名称");
        goods.setPrice(new BigDecimal("100"));
        goods.setAmount(1);
    }

    @Override
    public BigDecimal settleAccounts() {
        return goods.getPrice();
    }
}
