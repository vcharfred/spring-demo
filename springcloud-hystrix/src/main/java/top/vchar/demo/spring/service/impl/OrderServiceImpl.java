package top.vchar.demo.spring.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import top.vchar.demo.spring.pojo.ProductOrder;
import top.vchar.demo.spring.service.OrderService;
import top.vchar.demo.spring.service.ProductClient;
import top.vchar.demo.spring.utils.JsonUtil;

import java.util.Map;

/**
 * <p>  TODO 功能描述 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/8/27 22:35
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RestTemplate restTemplate;
    @Override
    public String update(int id, String name) {
        Map<String, Object> product = restTemplate.getForObject("http://product-service/api/v1/product/edit?id="+id+"&name="+name, Map.class);
        if(product==null){
            return "{'code':'404', 'msg':'无该产品信息'}";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{").append("'code':'200'");
        product.forEach((k, v)->sb.append(",'").append(k).append("':'").append(v).append("'"));
        sb.append("}");
        return sb.toString();
    }


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
