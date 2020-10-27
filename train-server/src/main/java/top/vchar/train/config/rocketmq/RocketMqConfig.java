package top.vchar.train.config.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import top.vchar.train.config.rocketmq.consumer.TrainPayRocketConsumer;

import javax.annotation.PostConstruct;

/**
 * <p> rocketmq配置 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/27
 */
@Slf4j
@Configuration
public class RocketMqConfig {

    @PostConstruct
    public void initConsumer(){
        new TrainPayRocketConsumer("pay", "*").init();
    }


}
