package top.vchar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import top.vchar.service.RibbonDemoService;

/**
 * <p> 使用ribbon的示例 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/5/10
 */
@Service
public class RibbonDemoServiceImpl implements RibbonDemoService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    public void findGoods1(Long id) {
        // 通过ribbon来获取要调用服务的一个地址和端口号
        ServiceInstance instance = loadBalancerClient.choose("ribbon-b");
        // 拼接调用的地址
        String url = String.format("http://%s:%s/goods?id=%d", instance.getHost(), instance.getPort(), id);
        // 发送请求
        String res = restTemplateBuilder.build().getForObject(url, String.class);
        System.out.println("方式一请求结果：" + res);
    }

    public void findGoods2(Long id) {
        String res = restTemplate.getForObject("http://ribbon-b/goods?id=" + id, String.class);
        System.out.println("方式二请求结果：" + res);
    }

    @Override
    public void findGoods(Long id) {
        // 方式一：通过LoadBalancerClient来获取服务地址
        findGoods1(id);
        // 方式二：直接使用spring封装过的RestTemplate
        findGoods2(id);
    }

}
