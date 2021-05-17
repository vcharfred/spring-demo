package top.vchar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * <p> feign示例服务B启动类 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/5/17
 */
@EnableDiscoveryClient
@SpringBootApplication
public class FeignApplicationServerB {

    public static void main(String[] args) {
        SpringApplication.run(FeignApplicationServerB.class, args);
    }
}
