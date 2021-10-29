package top.vchar.demo.spring.mq.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * <p> 消息队列：消费者2 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/25 22:02
 */
@Component
public class OrderConsumer2 {

    @JmsListener(destination = "order.queue")
    public void orderConsumer2(String text) throws InterruptedException {
        System.out.println(System.currentTimeMillis()+"order.queue接收到消息2："+text);
        Thread.sleep(5000);
        System.out.println(System.currentTimeMillis()+"order.queue处理完成消息2："+text);
    }

}
