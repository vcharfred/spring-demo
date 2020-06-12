package top.vchar.demo.spring.config;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import java.util.Map;

/**
 * <p> 配置动态读取 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/10/6 20:15
 */
public class ACMConfigListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent applicationEnvironmentPreparedEvent) {
        ConfigurableEnvironment environment = applicationEnvironmentPreparedEvent.getEnvironment();
        MutablePropertySources propertySources = environment.getPropertySources();
        for (PropertySource propertySource:propertySources){
            if(propertySource instanceof OriginTrackedMapPropertySource){
                Map config = (Map) propertySource.getSource();
                System.out.println("spring.redis.password："+config.get("spring.redis.password"));
                config.put("spring.redis.password", "123456");
            }
        }

    }
}
