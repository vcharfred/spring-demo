package top.vchar.demo.spring.mq;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.Queue;

/**
 * <p>  启动类 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/7 23:41
 */
@SpringBootApplication
@EnableJms//开启jms
public class StartApplication {

    public static void main(String[] args){
        SpringApplication.run(StartApplication.class);
    }

    //注入队列，交给spring管理
    @Bean
    public Queue queue(){
        //指定队列名称
        return new ActiveMQQueue("common.queue");
    }

}
