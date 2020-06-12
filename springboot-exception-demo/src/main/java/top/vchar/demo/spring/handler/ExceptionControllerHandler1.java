package top.vchar.demo.spring.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import top.vchar.demo.spring.exception.MyException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> 全局异常处理 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/10 21:29
 */
@ControllerAdvice
public class ExceptionControllerHandler1 {

    //全局Exception
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handlerException(Exception e, HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        map.put("code", 500);
        map.put("message", e.getMessage());
        map.put("url", request.getRequestURL());
        return map;
    }


    //自定义异常，这里返回页面，也可以返回数据
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public Object handlerMyException(MyException e, HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error.html");
        modelAndView.addObject("msg", e.getMessage());
        return modelAndView;
    }

}
