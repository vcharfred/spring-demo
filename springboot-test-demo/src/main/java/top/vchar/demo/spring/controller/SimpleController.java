package top.vchar.demo.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>  TODO 功能描述 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/8 23:16
 */
@RestController
public class SimpleController {

    @GetMapping("/get/version")
    public String home(){
        System.out.println("==ok==");
        return "1.0";
    }

}
