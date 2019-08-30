package top.vchar.demo.spring.service.impl;

import org.springframework.stereotype.Service;
import top.vchar.demo.spring.pojo.Product;
import top.vchar.demo.spring.service.ProductService;

import java.util.*;

/**
 * <p> 商品服务接口实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/8/4 21:58
 */
@Service
public class ProductServiceImpl implements ProductService {

    private static Map<Integer, Product> productDao = new HashMap<>();
    static {
        for(int i=1; i<10; i++){
            Product product = new Product();
            product.setId(i);
            product.setName("iPhone-"+i);
            product.setPrice(i*1000);
            product.setStore(i*20);
            productDao.put(i, product);
        }
    }

    @Override
    public List<Product> findAllProduct() {
        Collection<Product> collection = productDao.values();
        return new ArrayList<>(collection);
    }

    @Override
    public Product findProductById(int id) {
        return productDao.get(id);
    }

    @Override
    public Product edit(int id, String name) {
        Product product = productDao.get(id);
        if(product!=null){
            product.setName(name);
            productDao.put(id, product);
            return product;
        }
        return null;
    }
}
