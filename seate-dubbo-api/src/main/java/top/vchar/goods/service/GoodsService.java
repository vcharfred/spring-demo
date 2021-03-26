package top.vchar.goods.service;

/**
 * <p> 商品接口 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/3/26
 */
public interface GoodsService {

    /**
     * 扣除库存
     *
     * @param id 商品ID
     * @param num 本次扣除的数量
     * @return 返回结果
     */
    boolean deductInventory(Long id, int num);

}
