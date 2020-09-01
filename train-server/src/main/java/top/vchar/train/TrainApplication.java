package top.vchar.train;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * <p> 火车票服务 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/9/1
 */
@SpringBootApplication
public class TrainApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(TrainApplication.class).web(WebApplicationType.REACTIVE).run(args);
    }
}
