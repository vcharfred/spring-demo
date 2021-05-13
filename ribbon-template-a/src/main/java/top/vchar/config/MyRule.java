package top.vchar.config;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.Server;

/**
 * <p> 自定义规则 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/5/13
 */
public class MyRule extends AbstractLoadBalancerRule {

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

    @Override
    public Server choose(Object key) {
        return null;
    }
}
