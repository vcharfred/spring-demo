package top.vchar;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * <p> 服务提供者 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/2/26
 */
@SpringBootApplication
public class DubboProviderApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DubboProviderApplication.class)
                .web(WebApplicationType.SERVLET).run(args);
    }
}
