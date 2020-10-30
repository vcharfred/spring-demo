package top.vchar.rocketmq.config.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import top.vchar.rocketmq.config.rocketmq.bean.RocketMQProperties;
import top.vchar.rocketmq.config.rocketmq.consumer.OrderRocketConsumer;
import top.vchar.rocketmq.config.rocketmq.consumer.SimpleRocketConsumer;
import top.vchar.rocketmq.config.rocketmq.handler.RocketConsumerHandler;
import top.vchar.rocketmq.util.SpringBeanUtil;

import java.util.List;

/**
 * <p> 消费者注册 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/29
 */
@Slf4j
@Component
public class RocketMQConsumerRegister implements CommandLineRunner {

    private final RocketMQProperties properties;

    public RocketMQConsumerRegister(RocketMQProperties properties) {
        this.properties = properties;
    }

    @Override
    public void run(String... args) throws Exception {
        List<RocketMQProperties.Consumer> consumers = properties.getConsumer();
        if(consumers!=null && !consumers.isEmpty()){
            for(RocketMQProperties.Consumer consumer:consumers){
                if(consumer.getType()==0){
                    SimpleRocketConsumer.builder()
                            .nameServer(this.properties.getNameServer())
                            .consumerGroup(consumer.getConsumerGroup())
                            .consumeFromWhere(consumer.getConsumeFromWhere())
                            .topics(consumer.getTopics())
                            .tags(consumer.getTags())
                            .rocketConsumerHandler(getHandler(consumer.getRocketConsumerHandler()))
                            .number(consumer.getNumber())
                            .build().init();
                }else {
                    OrderRocketConsumer.builder()
                            .nameServer(this.properties.getNameServer())
                            .consumerGroup(consumer.getConsumerGroup())
                            .topics(consumer.getTopics())
                            .tags(consumer.getTags())
                            .rocketConsumerHandler(getHandler(consumer.getRocketConsumerHandler()))
                            .number(consumer.getNumber())
                            .build().init();
                }
            }
        }
    }

    private RocketConsumerHandler getHandler(String handlerClass){
        try {
            return (RocketConsumerHandler) SpringBeanUtil.getBean(Class.forName(handlerClass));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



}
