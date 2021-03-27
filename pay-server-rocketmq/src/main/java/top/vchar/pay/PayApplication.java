package top.vchar.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * <p> 支付服务启动类 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/27
 */
@EnableDiscoveryClient
@SpringBootApplication
public class PayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }
}