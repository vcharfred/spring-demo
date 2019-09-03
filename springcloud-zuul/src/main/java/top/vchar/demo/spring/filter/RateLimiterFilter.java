package top.vchar.demo.spring.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * <p> 限流 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/9/2 22:48
 */
@Component
public class RateLimiterFilter extends ZuulFilter {

    private static final RateLimiter ORDER_LIMIT = RateLimiter.create(1000);

    @Override
    public String filterType() {
        return FilterConstants.ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return -4;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        System.out.println(request.getRequestURI());
        if("/openapi/api/v2/order/save".equalsIgnoreCase(request.getRequestURI())){
            //拦截
            System.out.println("拦截");
//            return true;
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        if(!ORDER_LIMIT.tryAcquire()){
            RequestContext currentContext = RequestContext.getCurrentContext();
            currentContext.setSendZuulResponse(false);//阻止进行运行
            currentContext.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
        }
        return null;
    }
}
