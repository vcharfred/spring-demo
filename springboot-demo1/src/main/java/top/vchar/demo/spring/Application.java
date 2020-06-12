package top.vchar.demo.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> 启动类 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/7 23:03
 */
@SpringBootApplication
@RestController
public class Application {

    public static void main(String[] args){
        SpringApplication.run(Application.class);
    }

    @RequestMapping("/")
    public String home(){
        return "hello word";
    }

}