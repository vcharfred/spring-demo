package top.vchar.goods.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.vchar.goods.service.GoodsService;

import java.sql.SQLException;

/**
 * <p> 商品业务接口 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/3/28
 */
@RestController
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/goods")
    public String deductInventory() throws SQLException {
        boolean res = this.goodsService.deductInventory(1L, 2);
        if (res) {
            return "成功";
        }
        return "失败";
    }
}
