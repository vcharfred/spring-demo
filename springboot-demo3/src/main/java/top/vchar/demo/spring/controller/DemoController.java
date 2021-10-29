package top.vchar.demo.spring.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.vchar.demo.spring.pojo.Member;

/**
 * <p> controller使用 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/7 23:43
 */
@RestController
public class DemoController {

    @RequestMapping("/")
    public String home(){
        return "hello word";
    }

    /**
     * 使用bean对象传参
     * @param member 用户信息
     */
    @RequestMapping("/v1/save_user")
    public String saveUser1(Member member){
        if(null==member){
            return "save fail";
        }
        System.out.println(member.toString());
        return "save success";
    }

    /**
     * 使用bean对象传参
     * 注意：a.需要指定http头为Content-Type为application/json
     *      b.使用body传送参数
     * @param member 用户信息
     */
    @RequestMapping("/v2/save_user")
    public String saveUser2(@RequestBody Member member){
        if(null==member){
            return "save fail";
        }
        System.out.println(member.toString());
        return "save success";
    }

    /**
     * 获取http头信息
     */
    @RequestMapping("/get_header")
    public String saveUser3(@RequestHeader(value = "access_token", required = false) String accessToken){
        return accessToken;
    }


}
