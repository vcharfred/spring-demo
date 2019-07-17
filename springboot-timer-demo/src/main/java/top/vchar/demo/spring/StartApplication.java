package top.vchar.demo.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <p>  启动类 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/7 23:41
 */
@SpringBootApplication
@EnableAsync
@EnableScheduling
public class StartApplication {

    public static void main(String[] args){
        SpringApplication.run(StartApplication.class);
    }

}
