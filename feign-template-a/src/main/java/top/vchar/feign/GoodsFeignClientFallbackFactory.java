package top.vchar.feign;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import top.vchar.entity.Goods;

/**
 * <p>  TODO 功能描述 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/5/17
 */
@Component
public class GoodsFeignClientFallbackFactory implements FallbackFactory<GoodsFeignClientDemo4> {
    @Override
    public GoodsFeignClientDemo4 create(Throwable throwable) {
        // 这里通过匿名实现
        return new GoodsFeignClientDemo4() {
            @Override
            public Goods findById(Long id) {
                System.out.println(id + "请求接口异常: " + throwable.getMessage());
                return null;
            }
        };
    }
}
