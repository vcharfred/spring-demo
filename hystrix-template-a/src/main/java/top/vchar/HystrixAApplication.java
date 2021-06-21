package top.vchar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <p>  启动类 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/6/20
 */
@EnableHystrix
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class HystrixAApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixAApplication.class, args);
    }

}
