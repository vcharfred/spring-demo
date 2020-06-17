package top.vchar.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.vchar.goods.dto.DeductInventoryDTO;
import top.vchar.goods.dto.GoodsDetailDTO;
import top.vchar.goods.entity.Goods;

/**
 * <p> 商品业务接口 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/12
 */
public interface IGoodsService extends IService<Goods> {

    /**
     * 通过商品编号查询商品
     *
     * @param goodNo 商品编号
     * @return 返回商品信息
     */
    GoodsDetailDTO findGoodsByGoodsNo(String goodNo);

    /**
     * 扣除库存
     *
     * @param deductInventoryDTO 参数
     * @return 返回结果
     */
    Boolean deductInventory(DeductInventoryDTO deductInventoryDTO);
}
