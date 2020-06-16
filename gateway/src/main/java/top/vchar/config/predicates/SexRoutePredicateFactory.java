package top.vchar.config.predicates;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * <p> 性别断言工厂 </p>
 * <p>
 * 类名字必须以RoutePredicateFactory结尾
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/16
 */
@Component
public class SexRoutePredicateFactory extends AbstractRoutePredicateFactory<SexRoutePredicateFactory.Config> {

    public SexRoutePredicateFactory() {
        super(SexRoutePredicateFactory.Config.class);
    }

    /**
     * 从配置中获取配置的信息并赋值给配置类
     *
     * @return 返回配置信息
     */
    @Override
    public List<String> shortcutFieldOrder() {
        // 这里的顺序需要和配置文件中的一致
        return Collections.singletonList("sex");
    }

    /**
     * 定义匹配规则
     *
     * @param config 配置信息
     * @return 返回结果
     */
    @Override
    public Predicate<ServerWebExchange> apply(SexRoutePredicateFactory.Config config) {
        return new Predicate<ServerWebExchange>() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                // 获取请求中的参数
                String sex = serverWebExchange.getRequest().getQueryParams().getFirst("sex");
                if (StringUtils.isBlank(sex)) {
                    return false;
                }
                return Integer.parseInt(sex) == 0;
            }
        };
    }

    /**
     * 配置信息实体类
     */
    @Validated
    @Data
    public static class Config {
        @NotNull
        private Integer sex;
    }
}
