package top.vchar.config.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * <p> 权限过滤器 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/16
 */
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    /**
     * 过滤器优先级，越小优先级越高
     */
    public static final int AUTH_GLOBAL_FILTER_FILTER_ORDER = 10;

    /**
     * 过滤器逻辑
     *
     * @param exchange ServerWebExchange
     * @param chain    GatewayFilterChain
     * @return 返回结果
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getQueryParams().getFirst("token");
        if (!StringUtils.equals(token, "admin")) {
            // 拦截请求
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        // 放开通行
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return AUTH_GLOBAL_FILTER_FILTER_ORDER;
    }
}
