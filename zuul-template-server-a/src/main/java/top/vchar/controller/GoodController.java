package top.vchar.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> 商品服务 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2022/4/29
 */
@RestController
public class GoodController {

    @GetMapping
    public String findGoods(String id) {
        return "商品查询成功";
    }

}
