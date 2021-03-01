package top.vchar.goods.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import top.vchar.goods.entity.Goods;

/**
 * <p> 商品数据操作接口 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/3/1
 */
public interface GoodsDao extends IService<Goods> {

    /**
     * 库存扣除
     * @param id 商品ID
     * @param num 商品数量更新
     * @return 返回更新结果
     */
    boolean deductInventory(Long id, int num);
}
