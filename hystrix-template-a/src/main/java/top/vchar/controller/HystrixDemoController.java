package top.vchar.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.vchar.entity.Train;
import top.vchar.service.HystrixDemoService;

/**
 * <p> 示例 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/6/22
 */
@RestController
public class HystrixDemoController {

    @Autowired
    private HystrixDemoService hystrixDemoService;

    @HystrixCommand(fallbackMethod = "hystrixMethodDemoFail")
    @GetMapping("/demo1")
    public Train demo1() {
        System.out.println("执行...demo1");
        return this.hystrixDemoService.hystrixMethodDemo1();
    }

    /**
     * 这里一定要和HystrixCommand注解中的方法一致，且参数也必须一致；当服务异常时会调用此方法
     */
    private Train hystrixMethodDemoFail() {
        System.out.println("调用异常");
        return null;
    }

    @GetMapping("/demo2")
    public Train demo2() {
        System.out.println("执行...demo2");
        return this.hystrixDemoService.hystrixMethodDemo2();
    }

    @GetMapping("/demo3")
    public Train demo3() {
        System.out.println("执行...demo3");
        return this.hystrixDemoService.hystrixMethodDemo3();
    }


    @GetMapping("/demo4")
    public Train demo4() {
        System.out.println("执行...demo4");
        return this.hystrixDemoService.hystrixMethodDemo4();
    }
}
