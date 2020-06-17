package top.vchar.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import top.vchar.dto.MallOrderDTO;
import top.vchar.entity.MallOrder;
import top.vchar.feign.GoodsFeignClient;
import top.vchar.goods.dto.DeductInventoryDTO;
import top.vchar.goods.dto.GoodsDetailDTO;
import top.vchar.mapper.MallOrderMapper;
import top.vchar.service.MallService;

/**
 * <p> 商城业务接口实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/17
 */
@Service
public class MallServiceImpl extends ServiceImpl<MallOrderMapper, MallOrder> implements MallService {

    private final GoodsFeignClient goodsFeignClient;

    public MallServiceImpl(GoodsFeignClient goodsFeignClient) {
        this.goodsFeignClient = goodsFeignClient;
    }

    /**
     * 下单
     *
     * @param goodsNo 商品ID
     * @return 返回下单结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String booking(String goodsNo) {

        GoodsDetailDTO goodsDTO = goodsFeignClient.findGoodsDetailByGoodsNo(goodsNo);
        Assert.notNull(goodsDTO, "商品信息不存在");

        MallOrder mallOrder = new MallOrder();
        mallOrder.setAmount(1);
        mallOrder.setGoodsNo(goodsNo);
        mallOrder.setOrderNo("M" + System.currentTimeMillis());
        mallOrder.setGoodsName(goodsDTO.getGoodsName());
        mallOrder.setPrice(goodsDTO.getPrice());
        mallOrder.setUserId(1);
        mallOrder.setUserName("张三");

        // 扣除库存
        DeductInventoryDTO deductInventoryDTO = new DeductInventoryDTO();
        deductInventoryDTO.setGoodNo(mallOrder.getGoodsNo());
        deductInventoryDTO.setAmount(mallOrder.getAmount());
        Boolean ok = goodsFeignClient.deductInventory(deductInventoryDTO);
        Assert.isTrue(ok, "库存不足");

        this.save(mallOrder);

        return mallOrder.getOrderNo();
    }

    /**
     * 查询商城订单信息
     *
     * @param orderNo 订单号
     * @return 返回商城订单信息
     */
    @Override
    public MallOrderDTO getOrder(String orderNo) {
        LambdaQueryWrapper<MallOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MallOrder::getOrderNo, orderNo);
        MallOrder mallOrder = this.getOne(queryWrapper, false);
        if (mallOrder != null) {
            MallOrderDTO mallOrderDTO = new MallOrderDTO();
            BeanUtils.copyProperties(mallOrder, mallOrderDTO);
            return mallOrderDTO;
        }
        return null;
    }
}
