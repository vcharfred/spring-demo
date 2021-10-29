package top.vchar.demo.spring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.vchar.demo.spring.exception.MyException;

/**
 * <p> 异常测试 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/10 21:21
 */
@RestController
public class ExceptionController {

    @RequestMapping("/exc")
    public int home(int i){
        return 10/i;
    }

    @RequestMapping("/myexc")
    public int myException(){
        throw new MyException("500", "系统异常");
    }


}
