package top.vchar.demo.spring.service;

import top.vchar.demo.spring.pojo.Product;

import java.util.List;

/**
 * <p> 产品服务接口 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/8/4 21:43
 */
public interface ProductService {

    List<Product> findAllProduct();

    Product findProductById(int id);

    Product edit(int id, String name);
}
