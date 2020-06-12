package top.vchar.demo.spring.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * <p> 自定义ServletContextListener </p>
 * 用于资源初始化
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/17 21:50
 */
@WebListener
public class CustomContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("====CustomContextListener contextInitialized=======");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("====CustomContextListener contextDestroyed=======");
    }
}
