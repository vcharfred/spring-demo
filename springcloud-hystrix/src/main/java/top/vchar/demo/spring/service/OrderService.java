package top.vchar.demo.spring.service;

import top.vchar.demo.spring.pojo.ProductOrder;

/**
 * <p>  TODO 功能描述 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/8/27 22:34
 */
public interface OrderService {

    String update(int id, String name);

    ProductOrder findProductById(int id);
}
