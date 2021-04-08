package top.vchar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * <p> 启动类 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/3/27
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SeataDubboAApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeataDubboAApplication.class, args);
    }

}
