package top.vchar.demo.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * <p> 注入配置文件 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/8 22:51
 */
@Configuration
@PropertySource(value="classpath:resource.properties")
public class ServerConfig2 {

    @Value("${soft.domain}")
    private String domain;
    @Value("${soft.name}")
    private String name;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getSoftname() {
        return name;
    }

    public void setSoftname(String softname) {
        this.name = softname;
    }

    @Override
    public String toString() {
        return "ServerConfig{" +
                "domain='" + domain + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
