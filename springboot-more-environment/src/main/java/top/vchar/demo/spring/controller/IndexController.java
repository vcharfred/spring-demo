package top.vchar.demo.spring.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>  TODO 功能描述 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/30 22:44
 */
@RestController
public class IndexController {

    @Value("${system.environment}")
    private String environment;

    @GetMapping("/index")
    public String index(){
        return environment;
    }
}
