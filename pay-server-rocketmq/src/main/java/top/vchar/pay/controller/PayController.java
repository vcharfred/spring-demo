package top.vchar.pay.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import top.vchar.pay.dto.PayDTO;
import top.vchar.pay.service.PayOrderService;

/**
 * <p> 支付 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/27
 */
@RestController
public class PayController {

    private final PayOrderService payOrderService;

    public PayController(PayOrderService payOrderService) {
        this.payOrderService = payOrderService;
    }

    /**
     * 去支付
     * @param payDTO 支付参数
     * @return 返回处理结果
     */
    @PostMapping("/toPay")
    public Mono<String> pay(@RequestBody PayDTO payDTO){
        return payOrderService.pay(payDTO);
    }
}
