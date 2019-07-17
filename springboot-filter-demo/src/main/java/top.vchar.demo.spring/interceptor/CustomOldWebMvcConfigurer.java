package top.vchar.demo.spring.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * <p> 2.x以前注册Interceptor  </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/17 22:00
 */
@Configuration
public class CustomOldWebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/v2/*");

    }
}
