package top.vchar.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.vchar.service.MemberService;


/**
 * <p>  TODO 功能描述 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/16
 */
@RestController
@RequestMapping("/member")
public class IndexController {

    private final MemberService memberService;

    public IndexController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/getName")
    public String findNameById(Integer id) {
        return memberService.findNameById(id);
    }

}
