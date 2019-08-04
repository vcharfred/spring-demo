package top.vchar.demo.spring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> 首页 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/8/4 11:50
 */
@RestController
public class IndexController {

    @RequestMapping("/home")
    public String home(){
        return "spring boot2 start ok";
    }
}
