package top.vchar.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.vchar.order.service.SentinelDemoService;

/**
 * <p> Sentinel 示例 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/3/16
 */
@RestController
@RequestMapping("/sentinel")
public class SentinelDemoController {

    @Autowired
    private SentinelDemoService sentinelDemoService;

    /**
     * sentinel 熔断限流示例1
     *
     * @return 返回结果
     */
    @GetMapping("/demo1")
    public String sentinelDemo1(){
        return this.sentinelDemoService.sentinelDemo1();
    }

    /**
     * sentinel 熔断限流示例2
     *
     * @return 返回结果
     */
    @GetMapping("/demo2")
    public String sentinelDemo2(){
        return this.sentinelDemoService.sentinelDemo2();
    }

}
