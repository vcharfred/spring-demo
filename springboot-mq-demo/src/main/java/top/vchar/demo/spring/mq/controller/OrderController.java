package top.vchar.demo.spring.mq.controller;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.vchar.demo.spring.mq.service.ProducerService;

import javax.jms.Destination;

/**
 * <p> 消息发送暴露 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/25 21:15
 */
@RestController
@RequestMapping("/api/v1")
public class OrderController {

    @Autowired
    private ProducerService producerService;

    @GetMapping("/order")
    public String order(String msg){
        System.out.println("order: "+msg);
        //生成队列地址
        Destination destination = new ActiveMQQueue("order.queue");
        producerService.sendMessage(destination, msg);
        return "ok";
    }

    @GetMapping("/common")
    public String common(String msg){
        System.out.println("common: "+msg);
        producerService.sendMessage(msg);
        return "ok";
    }

}
