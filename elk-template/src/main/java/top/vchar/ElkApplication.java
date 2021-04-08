package top.vchar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <p> start class </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/4/8
 */
@EnableScheduling
@SpringBootApplication
public class ElkApplication {

    public static void main(String[] args) {

        SpringApplication.run(ElkApplication.class, args);
    }
}
