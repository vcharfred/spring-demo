package top.vchar.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.vchar.goods.dto.GoodsDetailDTO;
import top.vchar.goods.entity.Goods;
import top.vchar.goods.mapper.GoodsMapper;
import top.vchar.goods.service.IGoodsService;

/**
 * <p> 商品业务接口实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/12
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    /**
     * 通过商品编号查询商品
     * @param goodNo 商品编号
     * @return 返回商品信息
     */
    @Override
    public GoodsDetailDTO findGoodsByGoodsNo(String goodNo) {
        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Goods::getGoodsNo, goodNo);
        Goods goods = this.getOne(queryWrapper, false);
        if(goods!=null){
            GoodsDetailDTO goodsDetailDTO = new GoodsDetailDTO();
            BeanUtils.copyProperties(goods, goodsDetailDTO);
            return goodsDetailDTO;
        }
        return null;
    }
}
