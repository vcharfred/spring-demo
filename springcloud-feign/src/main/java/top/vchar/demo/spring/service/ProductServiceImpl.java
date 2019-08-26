package top.vchar.demo.spring.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.vchar.demo.spring.pojo.ProductOrder;
import top.vchar.demo.spring.utils.JsonUtil;

/**
 * <p>  TODO 功能描述 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/8/26 23:06
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductClient productClient;

    @Override
    public ProductOrder findProductById(int id) {
        String result = productClient.findProductById(id);
        JsonNode jsonNode = JsonUtil.str2JsonNode(result);
        ProductOrder productOrder = new ProductOrder();
        productOrder.setId(jsonNode.findValue("id").asInt());
        productOrder.setProductName(jsonNode.findValue("name").asText());
        productOrder.setPrice(jsonNode.findValue("price").asInt());
        return productOrder;
    }
}
