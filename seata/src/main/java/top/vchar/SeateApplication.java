package top.vchar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <p> 启动类 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/17
 */
@SpringBootApplication
@EnableFeignClients
public class SeateApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeateApplication.class, args);
    }

}
