package top.vchar.feign;

import feign.RequestInterceptor;
import feign.Retryer;
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

    /**
     * 配置feign的重试策略
     */
    @Bean
    public Retryer retryer() {
        // 这个feign提供的默认实现；可以自定义，实现 Retryer 接口即可
        return new Retryer.Default();
    }
}
