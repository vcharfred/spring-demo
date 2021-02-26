package top.vchar.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;
import top.vchar.goods.dto.GoodsDetailDTO;
import top.vchar.goods.service.GoodsService;
import top.vchar.order.service.OrderService;

/**
 * <p> 订单服务实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/2/26
 */
@Service
public class OrderServiceImpl implements OrderService {

    @DubboReference(interfaceClass = GoodsService.class)
    private GoodsService goodsService;

    /**
     * 预定
     * @param id 商品ID
     * @param num 数量
     * @return 返回预定结果
     */
    @Override
    public String booking(Long id, int num) {
        GoodsDetailDTO goods = this.goodsService.findGoodsById(id);
        System.out.println(JSONObject.toJSONString(goods));

        return goods.getGoodsName();
    }
}
