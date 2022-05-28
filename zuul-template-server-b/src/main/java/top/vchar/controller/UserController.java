package top.vchar.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> 用户服务 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2022/4/29
 */
@RestController
public class UserController {

    @GetMapping("/query")
    public String findUser(String id) {
        return "张三";
    }

    @GetMapping("/del")
    public String delUser(String id) {
        return "删除成功";
    }
}
