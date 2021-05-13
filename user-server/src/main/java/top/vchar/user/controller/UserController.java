package top.vchar.user.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import top.vchar.user.entity.UserInfo;
import top.vchar.user.service.ProfitsShareService;
import top.vchar.user.service.UserService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @Autowired
    private List<ProfitsShareService> profitsShareServices;

    @GetMapping("/share")
    public String profitsShare(String orgType) {
        Optional<ProfitsShareService> optional = this.profitsShareServices.stream().filter(p -> p.getOrgType().equals(orgType)).findFirst();
        Assert.isTrue(optional.isPresent(), "无该机构的分润实现方案");
        return optional.get().allocation(1L, 1, new BigDecimal(100));
    }

    /**
     * 通过用户ID查询用户名称
     *
     * @param id 用户ID
     * @return 返回用户名称
     */
    @GetMapping
    public String findUserName(Long id) {
        return userService.findById(id);
    }

    /**
     * 通过用户ID查询用户名称
     * @param name 用户名称
     * @return 返回用户名称
     */
    @GetMapping("/name")
    public String findUserName(String name){
        System.out.println(name);
        return name;
    }

    /**
     * 通过用户ID查询用户名称
     * @param userInfo 用户ID
     * @return 返回用户名称
     */
    @PostMapping
    public String addUserName(@RequestBody UserInfo userInfo){
        System.out.println(JSONObject.toJSONString(userInfo));
        return JSONObject.toJSONString(userInfo);
    }

    /**
     * 通过用户ID查询用户名称
     * @param userInfo 用户ID
     * @return 返回用户名称
     */
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, MultipartHttpServletRequest request){
        System.out.println(file.getOriginalFilename());
        Map<String, String[]> parameterMap = request.getParameterMap();
        return JSONObject.toJSONString(parameterMap);
    }


    @PostMapping("/add")
    public String add(@RequestBody UserInfo userInfo){
        return this.userService.addUser(userInfo);
    }

}
