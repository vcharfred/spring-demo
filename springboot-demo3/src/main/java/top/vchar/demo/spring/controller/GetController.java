package top.vchar.demo.spring.controller;

import org.springframework.web.bind.annotation.*;

/**
 * <p> 只允许GET请求 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/7 23:48
 */
@RestController
public class GetController {

    /**
     * 只允许GET请求
     */
    @RequestMapping(path = "/get/v1/user/{id}", method = RequestMethod.GET)
    public String findUserById1(@PathVariable("id") String uid){
        if("123456".equals(uid)){
            return "find it";
        }
        return "no this user";
    }

    /**
     * 只允许GET请求
     */
    @GetMapping(path = "/get/v2/user/{id}")
    public String findUserById2(@PathVariable("id") String uid){
        if("123456".equals(uid)){
            return "find it";
        }
        return "no this user";
    }
}
