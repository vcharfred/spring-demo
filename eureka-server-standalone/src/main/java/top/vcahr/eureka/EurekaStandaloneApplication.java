package top.vcahr.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * <p> eureka server 单机版启动 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/4/14
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaStandaloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaStandaloneApplication.class, args);
    }
}
