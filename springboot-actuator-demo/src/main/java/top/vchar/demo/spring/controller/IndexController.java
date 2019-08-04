package top.vchar.demo.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> 首页 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/8/4 10:07
 */
@RestController
public class IndexController {

    @GetMapping("/home")
    public String home(){
        return "ok";
    }
}
