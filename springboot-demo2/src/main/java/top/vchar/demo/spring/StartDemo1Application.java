package top.vchar.demo.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> springboot启动类，使用@SpringBootApplication注入相关配置 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/7 23:14
 */
@SpringBootApplication
@RestController
public class StartDemo1Application {

    public static void main(String[] args){
        SpringApplication.run(StartDemo1Application.class);
    }

    @RequestMapping("/")
    public String home(){
        return "hello word";
    }

}
