package top.vchar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * <p> 消费者启动类 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/2/26
 */
@EnableDiscoveryClient
@SpringBootApplication
public class DubboCustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboCustomerApplication.class, args);
    }

}
