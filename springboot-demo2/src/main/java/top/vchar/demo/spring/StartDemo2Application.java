package top.vchar.demo.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>springboot启动类，不使用@SpringBootApplication注入相关配置; 建议使用@SpringBootApplication，它已经包含这些注解了 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/7 23:17
 */
@RestController //相当于Controller+ResponseBody;即自动是ajax的请求
@EnableAutoConfiguration //spring 会自动装配相关的配置,这个是必须有的
@ComponentScan //根据spring的相关注解注入bean
public class StartDemo2Application {

    public static void main(String[] args){
        SpringApplication.run(StartDemo1Application.class);
    }

    @RequestMapping("/demo2")
    public String home(){
        return "hello word demo2";
    }

}
