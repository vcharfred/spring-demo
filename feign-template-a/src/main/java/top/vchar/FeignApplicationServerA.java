package top.vchar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <p> feign示例服务A启动类 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/5/17
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class FeignApplicationServerA {

    public static void main(String[] args) {
        SpringApplication.run(FeignApplicationServerA.class, args);
    }
}
