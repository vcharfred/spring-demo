package top.vchar.service.impl;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import top.vchar.service.NoRibbonDemoService;

import java.util.List;

/**
 * <p> 不使用ribbon </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/5/10
 */
@Service
public class NoRibbonDemoServiceImpl implements NoRibbonDemoService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    private EurekaClient eurekaClient;

    @Override
    public void findGoods(Long id) {
        // 从eureka注册表中获取到要调用服务的地址和端口号
        String domain = buildServerAddress("ribbon-b");
        // 拼接调用地址
        String url = String.format(domain + "/goods?id=%d", id);
        // 发送请求
        String res = restTemplateBuilder.build().getForObject(url, String.class);
        System.out.println(id + " 请求[" + url + "]结果：" + res);
    }

    private String buildServerAddress(String serverName) {
        Assert.notNull(serverName, "服务名称不能为空");
        Application application = eurekaClient.getApplication(serverName);
        Assert.notNull(application, "服务不可用");
        List<InstanceInfo> instances = application.getInstances();
        Assert.notNull(instances, "无可用服务");
        Assert.isTrue(instances.size() > 0, "无可用服务");
        // 负载均衡算法: 这里就使用简单的随机算法
        InstanceInfo instance = instances.get(RandomUtils.nextInt(instances.size()));
        return String.format("http://%s:%d", instance.getHostName(), instance.getPort());
    }

}
