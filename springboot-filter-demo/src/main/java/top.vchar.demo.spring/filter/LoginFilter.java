package top.vchar.demo.spring.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p> 过滤器 </p>
 *
 *  拦截以api开头的请求
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/17 21:09
 */
@WebFilter(urlPatterns = "/api/*", filterName = "loginFilter")
public class LoginFilter implements Filter {

    //初始化: 容器加载时调用
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("初始化 LoginFilter");
    }

    //请求被拦截的时候进行调用
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("执行 LoginFilter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = request.getParameter("token");
        if("1234".equals(token)){
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            System.out.println("LoginFilter 拦截请求");
            response.sendRedirect("/no_auth");
        }

    }

    //容器被销毁时调用
    @Override
    public void destroy() {
        System.out.println("销毁 LoginFilter");
    }
}
