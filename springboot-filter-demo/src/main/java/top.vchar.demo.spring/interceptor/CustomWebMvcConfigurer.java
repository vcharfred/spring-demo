package top.vchar.demo.spring.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p> 2.x以后注册Interceptor </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/17 21:59
 */
@Configuration
public class CustomWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/v2/*");

        //...注册其他拦截器

        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
