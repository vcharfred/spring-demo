package top.vchar.demo.spring.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.vchar.demo.spring.pojo.Product;
import top.vchar.demo.spring.service.ProductService;

import java.util.List;

/**
 * <p> 产品 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/8/4 21:43
 */
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Value("${server.port}")
    private String port;

    @Autowired
    private ProductService productService;

    @RequestMapping("/list")
    public List<Product> list(){
        return productService.findAllProduct();
    }

    @RequestMapping("/find")
    public Product findProductById(int id){
        Product product = productService.findProductById(id);
        Product result = new Product();
        BeanUtils.copyProperties(product, result);
        result.setName(product.getName()+" data from port="+port);
        return result;
    }
}
