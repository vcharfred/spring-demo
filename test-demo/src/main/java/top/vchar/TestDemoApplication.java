package top.vchar;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * <p> 启动类 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/9/3
 */
@SpringBootApplication
public class TestDemoApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(TestDemoApplication.class).web(WebApplicationType.SERVLET).run(args);
    }
}
