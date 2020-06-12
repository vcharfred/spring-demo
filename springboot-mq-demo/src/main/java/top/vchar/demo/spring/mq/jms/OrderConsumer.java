package top.vchar.demo.spring.mq.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * <p> 消息队列：消费者1 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/25 22:02
 */
@Component
public class OrderConsumer {

    @JmsListener(destination = "order.queue")
    public void orderConsumer(String text) throws InterruptedException {
        System.out.println(System.currentTimeMillis()+"order.queue接收到消息1："+text);
        Thread.sleep(5000);//5*30=150s=2
        System.out.println(System.currentTimeMillis()+"order.queue处理完成消息1："+text);
    }

}
