package top.vchar.demo.spring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>  启动类 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/7 23:41
 */
@SpringBootApplication
@MapperScan(value = {"top.vchar.demo.spring.mapper"})
public class TestDemoApplication {

    public static void main(String[] args){
        SpringApplication.run(TestDemoApplication.class);
    }

}
