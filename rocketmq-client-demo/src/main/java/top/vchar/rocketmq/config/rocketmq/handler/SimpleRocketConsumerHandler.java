package top.vchar.rocketmq.config.rocketmq.handler;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.vchar.rocketmq.config.rocketmq.ConsumerResult;
import top.vchar.rocketmq.dto.OrderDTO;

import java.math.BigDecimal;

/**
 * <p>  普通消息延时消息业务handler实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/29
 */
@Slf4j
@Component
public class SimpleRocketConsumerHandler implements RocketConsumerHandler {

    @Override
    public ConsumerResult handler(String message) {
        log.info("消费消息: {}", message);
        OrderDTO order = JSONObject.parseObject(message, OrderDTO.class);
        if(order.getAmount().compareTo(new BigDecimal("100"))==0){
            log.info("不再尝试：{}", order.getOrderNo());
            return new ConsumerResult(false, false);
        }
        if(order.getAmount().compareTo(new BigDecimal("200"))==0){
            log.info("再次尝试：{}", order.getOrderNo());
            return new ConsumerResult(false, true);
        }
        return new ConsumerResult(true);
    }
}
