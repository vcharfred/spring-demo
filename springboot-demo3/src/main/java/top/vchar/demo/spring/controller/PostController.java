package top.vchar.demo.spring.controller;

import org.springframework.web.bind.annotation.*;

/**
 * <p> 只允许POST请求 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/8 0:24
 */
@RestController
public class PostController {

    /**
     * 只允许POST请求
     */
    @RequestMapping(path = "/post/v1/user/{id}", method = RequestMethod.POST)
    public String findUserById1(@PathVariable("id") String uid){
        if("123456".equals(uid)){
            return "find it";
        }
        return "no this user";
    }

    /**
     * 只允许POST请求
     */
    @PostMapping(path = "/post/v2/user/{id}")
    public String findUserById2(@PathVariable("id") String uid){
        if("123456".equals(uid)){
            return "find it";
        }
        return "no this user";
    }

}
