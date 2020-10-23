package top.vchar.train;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * <p> 火车票服务 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/9/1
 */
@EnableDiscoveryClient
@SpringBootApplication
public class TrainApplication {

    public static void main(String[] args) {
        /// SpringApplication.run(TrainApplication.class, args);
        new SpringApplicationBuilder(TrainApplication.class).web(WebApplicationType.REACTIVE).run(args);
    }
}
