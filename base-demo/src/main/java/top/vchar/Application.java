package top.vchar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 使用@EnableAutoConfiguration注解来自动根据jar包的依赖进行相关配置
 *
 * 使用@RestController来标记仅仅提供RESTful接口，就像spring mvc中添加@ResponseBody来注明不走视图渲染
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
@RestController
public class Application {

    /**
     * 使用@RequestMapping注解设置请求路径，@PathVariable注解获取动态请求路径中变量值
     */
    @RequestMapping("/hello/{name}")
    public String hello(@PathVariable("name") String name){
        return "hello "+name;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
