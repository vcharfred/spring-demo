package top.vchar.eureka;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * <p> eureka注册中心 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/8/31
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaApplication.class).web(WebApplicationType.SERVLET).run(args);
    }
}
