package top.vchar.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import top.vchar.util.NetworkUtil;

import java.util.UUID;

/**
 * <p> 请求头添加额外参数 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/9/1
 */
@Slf4j
@Component
public class AdditionalHeaderFilter implements GlobalFilter, Ordered {

    public static final String REQUEST_TRACE_ID = "X-Trace-Id";
    public static final String REQUEST_IP = "X-Client-IP";

    public static final int ORDER = 8;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String ip = NetworkUtil.getIpAddress(exchange.getRequest());
        String uuid = UUID.randomUUID().toString();

        ServerHttpRequest request = exchange.getRequest().mutate()
                .header(REQUEST_TRACE_ID, uuid)
                .header(REQUEST_IP, ip)
                .build();
        return chain.filter(exchange.mutate().request(request).build());
    }

    @Override
    public int getOrder() {
        return ORDER;
    }
}
