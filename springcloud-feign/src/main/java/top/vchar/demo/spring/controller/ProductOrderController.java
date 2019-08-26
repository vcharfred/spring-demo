package top.vchar.demo.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.vchar.demo.spring.pojo.ProductOrder;
import top.vchar.demo.spring.service.ProductService;

/**
 * <p> 商品查询 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/8/26 23:22
 */
@RestController
@RequestMapping("/api/v2/order")
public class ProductOrderController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/find")
    public ProductOrder find(@RequestParam("id") int id){
        return productService.findProductById(id);
    }

}
