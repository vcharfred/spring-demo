package top.vchar.demo.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import top.vchar.demo.spring.config.ACMConfigListener;

/**
 * <p> 启动类 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/10/6 17:12
 */
@SpringBootApplication
public class ConfigApplication{

    public static void main(String[] args) {
//        SpringApplication springApplication = new SpringApplication(ConfigApplication.class);
//        springApplication.addListeners(new ACMConfigListener());
//        springApplication.run(args);RelaxedPropertyResolver

        SpringApplication.run(ConfigApplication.class);
//        configurableApplicationContext.close();
    }


}
