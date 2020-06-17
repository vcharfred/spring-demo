package top.vchar.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.vchar.dto.MallOrderDTO;
import top.vchar.service.MallService;

/**
 * <p> 商城 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/17
 */
@RestController
public class MallController {

    private final MallService mallService;

    public MallController(MallService mallService) {
        this.mallService = mallService;
    }

    @RequestMapping("/mall/booking")
    public String booking(String goodsNo) {
        return mallService.booking(goodsNo);
    }

    @GetMapping("/mall/order")
    public MallOrderDTO getOrder(@RequestParam("orderNo") String orderNo) {
        return mallService.getOrder(orderNo);
    }

}
