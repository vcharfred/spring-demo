package top.vchar.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.vchar.user.service.UserService;

/**
 * <p> 用户信息 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/8/31
 */
@RestController
@RequestMapping("/base")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 通过用户ID查询用户名称
     * @param id 用户ID
     * @return 返回用户名称
     */
    @GetMapping
    public String findUserName(Long id){
        return this.userService.findById(id);
    }

}
