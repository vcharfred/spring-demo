package top.vchar.goods.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.vchar.goods.dao.GoodsDao;
import top.vchar.goods.entity.Goods;
import top.vchar.goods.mapper.GoodsMapper;

/**
 * <p> 商品数据操作 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/3/1
 */
@Service
public class GoodsDaoImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsDao {

    /**
     * 库存扣除
     * @param id 商品ID
     * @param num 商品数量更新
     * @return 返回更新结果
     */
    @Override
    public boolean deductInventory(Long id, int num) {
        num = this.baseMapper.deductInventory(id, num);
        return num==1;
    }
}
