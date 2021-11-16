package top.vchar.demo.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * <p> 登录 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/11/16
 */
@RestController
public class UserController {

    @GetMapping("/user/login")
    public String login(String tel, String pwd){
        System.out.println("登录用户："+tel);
        return UUID.randomUUID().toString();
    }
}

