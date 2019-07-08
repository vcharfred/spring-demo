package top.vchar.demo.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * <p> 系统配置 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/8 22:38
 */
@Configuration
@PropertySource(value="classpath:resource.properties")
@ConfigurationProperties(prefix = "soft")
public class ServerConfig {


    private String domain;

    private String name;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ServerConfig{" +
                "domain='" + domain + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
