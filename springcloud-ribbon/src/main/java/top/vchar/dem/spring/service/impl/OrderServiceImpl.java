package top.vchar.dem.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import top.vchar.dem.spring.pojo.ProductOrder;
import top.vchar.dem.spring.service.OrderService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p> 订单接口实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/8/5 21:41
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static Map<Integer, ProductOrder> orderMap = new HashMap<>();

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Override
    public ProductOrder save1(int userId, int productId) {
        System.out.println("方式一");
        Map<String, Object> product = restTemplate.getForObject("http://product-service/api/v1/product/find?id="+productId, Map.class);
        return save(userId, productId, product);
    }

    @Override
    public ProductOrder save2(int userId, int productId) {
        System.out.println("方式二");
        ServiceInstance serviceInstance = loadBalancerClient.choose("product-service");
        String url = String.format("http://%s:%s/api/v1/product/find?id="+productId, serviceInstance.getHost(), serviceInstance.getPort());
        RestTemplate restTemplate1 = new RestTemplate();
        Map<String, Object> product = restTemplate1.getForObject(url, Map.class);
        return save(userId, productId, product);
    }

    private ProductOrder save(int userId, int productId, Map<String, Object> product){
        ProductOrder productOrder = new ProductOrder();
        productOrder.setUserId(userId);
        productOrder.setCreateTime(new Date());
        productOrder.setTradeNo(UUID.randomUUID().toString());
        productOrder.setProductId(productId);
        productOrder.setId(orderMap.size());
        productOrder.setProductName(product.get("name").toString());
        productOrder.setPrice(Integer.parseInt(product.get("price").toString()));
        orderMap.put(productOrder.getId(), productOrder);
        return productOrder;
    }


}
