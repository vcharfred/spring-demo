package top.vchar.demo.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p> 页面 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/8 1:07
 */
@Controller
public class PageController {

    @RequestMapping("/")
    public String home(){
        return "index";
    }

}
