package top.vchar.demo.spring.mq.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * <p> 监听消息：消费者 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/25 22:02
 */
@Component
public class OrderConsumer {

    @JmsListener(destination = "order.queue")
    public void orderConsumer(String text){
        System.out.println("接收到消息："+text);
    }

}
