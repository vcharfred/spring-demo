package top.vchar.feign;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * <p> feign配置 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/5/18
 */
public class FeignConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new CustomRequestInterceptor();
    }
}
