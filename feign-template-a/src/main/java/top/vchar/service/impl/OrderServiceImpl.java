package top.vchar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.vchar.entity.Goods;
import top.vchar.entity.GoodsParams;
import top.vchar.feign.GoodsFeignClientDemo1;
import top.vchar.feign.GoodsFeignClientDemo2;
import top.vchar.feign.GoodsFeignClientDemo3;
import top.vchar.feign.GoodsFeignClientDemo4;
import top.vchar.service.OrderService;

/**
 * <p> 订单业务接口实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/5/17
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private GoodsFeignClientDemo1 goodsFeignClientDemo1;


    @Override
    public void demo1() {
        Goods goods = goodsFeignClientDemo1.findById(1L);
        System.out.println("示例1：" + goods);
    }

    @Autowired
    private GoodsFeignClientDemo2 goodsFeignClientDemo2;

    @Override
    public void demo2() {
        Goods goods = goodsFeignClientDemo2.findById(1L);
        System.out.println("示例1：" + goods);

        goods = goodsFeignClientDemo2.findById(1L, "商品名称");
        System.out.println("示例2：" + goods);

        goods = goodsFeignClientDemo2.findById(GoodsParams.build());
        System.out.println("示例3：" + goods);

        goods = goodsFeignClientDemo2.findById(GoodsParams.build(), 1L);
        System.out.println("示例4：" + goods);

        goods = goodsFeignClientDemo2.findGoods(1L);
        System.out.println("示例5：" + goods);

        String res = goodsFeignClientDemo2.checkGoods(GoodsParams.build());
        System.out.println("示例6：" + res);
    }

    @Autowired
    private GoodsFeignClientDemo3 goodsFeignClientDemo3;

    @Override
    public void demo3() {
        Goods goods = goodsFeignClientDemo3.findById(2L);
        System.out.println("示例3：" + goods);
    }

    @Autowired
    private GoodsFeignClientDemo4 goodsFeignClientDemo4;

    @Override
    public void demo4() {
        Goods goods = goodsFeignClientDemo4.findById(2L);
        System.out.println("示例3：" + goods);
    }
}
