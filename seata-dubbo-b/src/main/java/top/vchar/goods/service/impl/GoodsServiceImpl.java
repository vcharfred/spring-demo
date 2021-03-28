package top.vchar.goods.service.impl;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import top.vchar.goods.entity.Goods;
import top.vchar.goods.mapper.GoodsMapper;
import top.vchar.goods.service.GoodsService;
import top.vchar.integral.service.IntegralService;

import java.sql.SQLException;

/**
 * <p> 商品接口实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/3/27
 */
@DubboService
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @DubboReference(interfaceClass = IntegralService.class, check = false)
    private IntegralService integralService;

    @Override
    public boolean deductInventory(Long id, int num) throws SQLException {
        Goods goods = goodsMapper.selectById(id);
        int count = goodsMapper.deductInventory(id, num);
        if (count != 1) {
            throw new SQLException("库存不足");
        }
        boolean res = this.integralService.deductIntegral(id, num * goods.getIntegral());
        System.out.println("积分扣除结果：" + res);
        if (!res) {
            throw new SQLException("积分不足");
        }
        return true;
    }
}
