package top.vchar.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;
import top.vchar.entity.Goods;
import top.vchar.entity.GoodsParams;

/**
 * <p> feign客户端调用接口示例 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/5/17
 */
@FeignClient(name = "feign-server-b", path = "/goods")
public interface GoodsFeignClientDemo2 {

    /**
     * GET 请求示例：只有一个参数
     */
    @GetMapping("/demo1")
    Goods findById(@RequestParam(value = "id") Long id);

    /**
     * GET 请求示例：多个参数
     */
    @GetMapping("/demo2")
    Goods findById(@RequestParam(value = "id") Long id, @RequestParam(value = "goodsName") String goodsName);

    /**
     * GET 请求示例：参数是一个对象
     */
    @GetMapping("/demo3")
    Goods findById(@SpringQueryMap GoodsParams params);

    /**
     * GET 请求示例：参数是一个对象
     */
    @GetMapping("/demo4")
    Goods findById(@SpringQueryMap GoodsParams params, @RequestParam(value = "id") Long id);

    /**
     * GET 请求示例：URL路径上的
     */
    @GetMapping("/demo5/{id}")
    Goods findGoods(@PathVariable(value = "id") Long id);

    /**
     * POST 请求
     */
    @PostMapping("/demo6")
    String checkGoods(@RequestBody GoodsParams params);

}
