package top.vchar.demo.spring.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


/**
 * <p>  自定义zuul过滤器 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/9/2 22:21
 */
@Component
public class LoginFilter extends ZuulFilter {
    @Override
    public String filterType() {
        //设置拦截器类型
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        //过滤器顺序，越小越先执行
        return 4;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        System.out.println(request.getRequestURI());
        if("/openapi/api/v2/order/find".equalsIgnoreCase(request.getRequestURI())){
            //拦截
            return true;
        }
        return false;

    }

    @Override
    public Object run() throws ZuulException {
        //业务逻辑

        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String token = request.getHeader("token");
        if(StringUtils.isBlank(token)){
            token = request.getParameter("token");
        }

        if(StringUtils.isBlank(token)){//token验证失败
            currentContext.setSendZuulResponse(false);//阻止进行运行
            currentContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }

        return null;
    }
}
