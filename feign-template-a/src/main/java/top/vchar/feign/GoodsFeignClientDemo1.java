package top.vchar.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.vchar.entity.Goods;

/**
 * <p> feign客户端调用接口示例 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/5/17
 */
@FeignClient(name = "feign-server-b") // 服务名称，必须匹配，否则待会将无法替换为真实的地址
public interface GoodsFeignClientDemo1 {

    /**
     * 这里的路径必须和对应要调用的接口地址对应
     */
    @GetMapping("/goods")
    Goods findById(@RequestParam(value = "id") Long id);
}
