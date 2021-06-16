package top.vchar.service;

import top.vchar.entity.Goods;

/**
 * <p> 商品接口类 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/5/17
 */
public interface GoodsService {

    Goods findById(Long id) throws InterruptedException;

}
