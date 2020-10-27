package top.vchar.pay.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;
import top.vchar.pay.dto.PayDTO;
import top.vchar.pay.entity.PayOrder;
import top.vchar.pay.mapper.PayOrderMapper;
import top.vchar.pay.service.PayOrderService;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * <p> 支付订单业务接口实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/27
 */
@Slf4j
@Service
public class PayOrderServiceImpl extends ServiceImpl<PayOrderMapper, PayOrder> implements PayOrderService {

    private final RocketMQTemplate rocketMQTemplate;

    public PayOrderServiceImpl(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    /**
     * 支付成功订单处理
     * @param payDTO 支付订单信息
     * @return 返回处理结果
     */
    @Override
    public Mono<String> pay(PayDTO payDTO) {
        // 存储支付订单
        log.info("存储订单信息:{}", payDTO);

        return Mono.defer(()-> Mono.just(savePayOrder(payDTO))).flatMap(p->{
            if(p){
                // 向业务系统推送支付成功通知
                log.info("推送rocket mq 消息");
                this.rocketMQTemplate.convertAndSend("pay:pay_success", payDTO);
                return Mono.just("操作成功");
            }
            return Mono.just("数据库异常");
        });
    }

    /**
     * 保存支付成功订单
     * @param payDTO 支付订单信息
     * @return 返回处理结果
     */
    @Override
    public boolean savePayOrder(PayDTO payDTO) {
        Assert.notNull(payDTO.getOrderNo(), "订单号不能为空");
        // 存储支付订单
        PayOrder payOrder = new PayOrder();
        payOrder.setOrderNo(payDTO.getOrderNo());
        payOrder.setCreateTime(LocalDateTime.now());
        payOrder.setAmount(payDTO.getAmount());
        payOrder.setNotify(0);
        payOrder.setNotifyOk(0);
        boolean save = this.save(payOrder);
        if(!save){
            log.error("订单支付成功，数据存储失败");
            return false;
        }
        return true;
    }
}
