package top.vchar.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * <p> 订单业务 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/9/3
 */
@Configurable
@Component
public class OrderService {

    @Autowired
    private GoodsService goodsService;

    public void booking(){
        BigDecimal price = goodsService.settleAccounts();
        System.out.println("价格："+price);
    }
}
