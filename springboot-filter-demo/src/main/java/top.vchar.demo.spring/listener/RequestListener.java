package top.vchar.demo.spring.listener;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

/**
 * <p> 自定义ServletRequestListener监听器 </p>
 *  主要用于请求监听统计处理
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/17 21:42
 */
@WebListener
public class RequestListener implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        System.out.println("=====请求初始化 RequestListener====");
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        System.out.println("====请求销毁 RequestListener=====");
    }
}
