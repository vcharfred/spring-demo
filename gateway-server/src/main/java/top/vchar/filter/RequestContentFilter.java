package top.vchar.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import top.vchar.util.NetworkUtil;

/**
 * <p> 安全验证拦截器 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/9/1
 */
@Slf4j
@Component
public class RequestContentFilter implements GlobalFilter, Ordered {

    public static final int ORDER = AdditionalHeaderFilter.ORDER+1;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 请求头拦截
        HttpHeaders headers = exchange.getRequest().getHeaders();
        boolean validateHeader = validateHeader(headers);
        if(validateHeader){
            return chain.filter(exchange);
        }
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        return Mono.empty();
    }

    @Override
    public int getOrder() {
        return ORDER;
    }

    private boolean validateHeader(HttpHeaders headers){
        String ip = headers.getFirst(AdditionalHeaderFilter.REQUEST_IP);
        if(null==ip || NetworkUtil.UNKNOWN.equals(ip)){
            return false;
        }
        // 支持的请求头类型：json、xml、multipart/form-data、multipart/mixed
        MediaType contentType = headers.getContentType();
        return contentType == MediaType.APPLICATION_JSON || contentType == MediaType.APPLICATION_STREAM_JSON
                || contentType == MediaType.TEXT_XML || contentType == MediaType.APPLICATION_XML
                || contentType == MediaType.TEXT_PLAIN
                || contentType == MediaType.MULTIPART_FORM_DATA || contentType == MediaType.MULTIPART_MIXED;
    }
}
