package top.vchar.demo.spring.service;

import top.vchar.demo.spring.pojo.ProductOrder;

/**
 * <p>  TODO 功能描述 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/8/26 23:06
 */
public interface ProductService {

    ProductOrder findProductById(int id);

}
