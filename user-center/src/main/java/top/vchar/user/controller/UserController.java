package top.vchar.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.vchar.user.dto.UserDetailDTO;
import top.vchar.user.service.IUserService;

/**
 * <p> 用户控制器 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/11
 */
@RequestMapping("/user")
@RestController
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/detail/{id}")
    public UserDetailDTO findUserById(@PathVariable("id") Integer id){
        return userService.findById(id);
    }
}
