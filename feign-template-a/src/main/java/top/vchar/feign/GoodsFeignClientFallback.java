package top.vchar.feign;

import org.springframework.stereotype.Component;
import top.vchar.entity.Goods;

/**
 * <p> feign异常处理 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/5/17
 */
@Component
public class GoodsFeignClientFallback implements GoodsFeignClientDemo3 {

    @Override
    public Goods findById(Long id) {
        System.out.println(id + "请求接口异常");
        return null;
    }
}
