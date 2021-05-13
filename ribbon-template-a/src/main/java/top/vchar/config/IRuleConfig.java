package top.vchar.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;

/**
 * <p> 注入配置 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/5/13
 */
@RibbonClient(name = "server-name", configuration = IRuleConfig.class)
public class IRuleConfig {

    @Bean
    public IRule getRule() {
        return new RandomRule();
    }

//    @Bean
//    public IRule getRule(){
//        return new MyRule();
//    }
}
