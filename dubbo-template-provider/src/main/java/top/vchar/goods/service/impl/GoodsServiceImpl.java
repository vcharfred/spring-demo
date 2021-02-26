package top.vchar.goods.service.impl;

import org.apache.dubbo.config.annotation.DubboService;
import top.vchar.goods.dto.GoodsDetailDTO;
import top.vchar.goods.service.GoodsService;

import java.math.BigDecimal;

/**
 * <p> 商品服务业务接口实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/2/26
 */
@DubboService
public class GoodsServiceImpl implements GoodsService {

    @Override
    public GoodsDetailDTO findGoodsById(Long id) {
        GoodsDetailDTO goods = new GoodsDetailDTO();
        goods.setGoodsNo(String.valueOf(id));
        goods.setGoodsName("手机");
        goods.setInventory(2);
        goods.setPrice(new BigDecimal("3000"));
        return goods;
    }

    @Override
    public boolean deductInventory(Long id, int num) {
        return false;
    }
}
