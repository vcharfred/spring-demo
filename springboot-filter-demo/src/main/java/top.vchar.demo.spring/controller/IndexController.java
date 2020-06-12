package top.vchar.demo.spring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> 首页 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/17 21:24
 */
@RestController
public class IndexController {

    @RequestMapping("/home")
    public String home(){
        return "ok";
    }

    @RequestMapping("/no_auth")
    public String noAuth(){
        return "权限不足";
    }

    @RequestMapping("/api/demo")
    public String apiDemo(){
        return "api";
    }


    @RequestMapping("/v1/demo")
    public String servletDemo(){
        return "servlet";
    }

}
