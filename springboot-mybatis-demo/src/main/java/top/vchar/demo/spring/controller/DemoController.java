package top.vchar.demo.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.vchar.demo.spring.entity.User;
import top.vchar.demo.spring.service.DemoService;

/**
 * <p>  TODO 功能描述 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/17 23:43
 */
@RestController
public class DemoController {

    @Autowired
    private DemoService demoService;

    @GetMapping("/add_user")
    public User addUser(String name, int age){
        User user = new User();
        user.setName(name);
        user.setAge(age);
        int i = demoService.addUser(user);
        return user;
    }

}
