package top.vchar.demo.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>  TODO 功能描述 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/8 21:54
 */
@RestController
public class HomeController {

    @GetMapping("/")
    public int home(int num){
        int result = 9;
        System.out.println("1234");
        return result*num;
    }

}
