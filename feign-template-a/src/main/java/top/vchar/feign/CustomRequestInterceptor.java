package top.vchar.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * <p> 自定义feign拦截器 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/5/18
 */
public class CustomRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        System.out.println("拦截请求...");
        requestTemplate.header("uid", "9527");
    }
}
