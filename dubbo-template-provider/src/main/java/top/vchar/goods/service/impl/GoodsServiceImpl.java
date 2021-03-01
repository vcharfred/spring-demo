package top.vchar.goods.service.impl;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import top.vchar.goods.dao.GoodsDao;
import top.vchar.goods.dto.GoodsDetailDTO;
import top.vchar.goods.entity.Goods;
import top.vchar.goods.service.GoodsService;


/**
 * <p> 商品服务业务接口实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/2/26
 */
@DubboService
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    @Override
    public GoodsDetailDTO findGoodsById(Long id) {
        Goods goods = goodsDao.getById(id);
        if(null!=goods){
            GoodsDetailDTO detail = new GoodsDetailDTO();
            BeanUtils.copyProperties(goods, detail);
            return detail;
        }
        return null;
    }

    @Override
    public boolean deductInventory(Long id, int num) {
        Goods goods = goodsDao.getById(id);
        if(null==goods){
            System.out.println("商品不存在");
            return false;
        }
        return goodsDao.deductInventory(id, num);
    }
}
