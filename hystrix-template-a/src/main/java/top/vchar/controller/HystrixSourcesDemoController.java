package top.vchar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.vchar.service.HystrixSourceDemoService;

/**
 * <p> 线程池资源隔离示例 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2022/1/4
 */
@RestController
public class HystrixSourcesDemoController {

    @Autowired
    private HystrixSourceDemoService hystrixSourceDemoService;


    @GetMapping("/sources1_1")
    public String findOne1(){
        return hystrixSourceDemoService.findOne1();
    }

    @GetMapping("/sources2_1")
    public String findMore(){
        return null;
    }
}
