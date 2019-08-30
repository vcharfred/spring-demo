package top.vchar.demo.spring.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.vchar.demo.spring.pojo.ProductOrder;
import top.vchar.demo.spring.service.OrderService;

/**
 * <p>  TODO 功能描述 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/8/27 22:32
 */
@RestController
@RequestMapping("/api/v3/order")
public class IndexController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("/update")
    @HystrixCommand(fallbackMethod = "updateOrderFail")
    public String update(@RequestParam("id") int id, @RequestParam("name")String name){
        return orderService.update(id, name);
    }

    //这里一定要和HystrixCommand注解中的方法一致，且参数也必须一致
    private String updateOrderFail(int id, String name){
        System.out.println("服务异常： "+id+" "+name);
        return "{'code':'-1', 'msg':'当前访问人数过多，请稍后再试'}";
    }

    @RequestMapping("/find")
    public ProductOrder find(@RequestParam("id") int id){
        return orderService.findProductById(id);
    }
}
