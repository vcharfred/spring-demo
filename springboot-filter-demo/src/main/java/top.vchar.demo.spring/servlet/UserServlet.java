package top.vchar.demo.spring.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p> 自定义Servlet </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/17 21:32
 */
@WebServlet(name = "userServlet", urlPatterns = "/v1/*")
public class UserServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("执行userServlet Get");
        resp.getWriter().println("custom servlet");
        resp.getWriter().flush();
        resp.getWriter().close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("执行userServlet Post");
        this.doGet(req, resp);
    }
}
