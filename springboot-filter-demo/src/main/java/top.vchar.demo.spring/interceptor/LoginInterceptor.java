package top.vchar.demo.spring.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p> 拦截器 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/17 22:09
 */
public class LoginInterceptor implements HandlerInterceptor {

    //进入controller方法前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("=====LoginInterceptor preHandle======");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    //调用完Controller之后，视图渲染之前；如果控制器Controller出现了异常，则不会执行此方法
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("=====LoginInterceptor postHandle======");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    //整个完成后，不管有没有异常，这个afterCompletion都会被调用，用于资源清理
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("=====LoginInterceptor afterCompletion======");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
