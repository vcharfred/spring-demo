package top.vchar.goods.service;

import top.vchar.goods.dto.GoodsDetailDTO;

/**
 * <p> 商品服务业务接口 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/2/26
 */
public interface GoodsService {

    /**
     * 通过ID查询商品详情
     * @param id 商品ID
     * @return 返回结果
     */
    GoodsDetailDTO findGoodsById(Long id);

    /**
     * 扣除库存
     *
     * @param id 商品ID
     * @param num 本次扣除的数量
     * @return 返回结果
     */
    boolean deductInventory(Long id, int num);

}
