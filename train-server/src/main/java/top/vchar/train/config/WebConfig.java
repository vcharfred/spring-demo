package top.vchar.train.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.PathMatchConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * <p> 配置 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/21
 */
@Configuration
@EnableWebFlux
public class WebConfig implements WebFluxConfigurer {

    @Override
    public void configurePathMatching(PathMatchConfigurer configurer) {
        // 为所有路由添加前缀
        configurer.addPathPrefix("/train", HandlerTypePredicate.forAnnotation(RestController.class));
    }
}
