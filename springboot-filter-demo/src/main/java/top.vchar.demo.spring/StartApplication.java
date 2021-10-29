package top.vchar.demo.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * <p>  启动类 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/7 23:41
 */
@SpringBootApplication
@ServletComponentScan//开启servlet注解
public class StartApplication extends SpringBootServletInitializer {

    public static void main(String[] args){
        SpringApplication.run(StartApplication.class);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(StartApplication.class);
    }
}
