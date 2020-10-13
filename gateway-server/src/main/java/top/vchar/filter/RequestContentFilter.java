package top.vchar.filter;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import top.vchar.common.response.ApiResponse;
import top.vchar.common.response.ApiResponseBuilder;
import top.vchar.util.NetworkUtil;

import java.nio.charset.StandardCharsets;

/**
 * <p> 请求头和请求内容验证拦截器 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/9/1
 */
@Slf4j
@Component
public class RequestContentFilter implements GlobalFilter, Ordered {

    public static final int ORDER = AdditionalHeaderFilter.ORDER+1;

    /**
     * 请求数据包最大5M
     */
    private static final long MAX_PACKAGE_SIZE = 1024*1024*5L;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 请求头信息验证
        HttpHeaders headers = exchange.getRequest().getHeaders();
        if (validateIp(headers)){
            return write(exchange, HttpStatus.NOT_ACCEPTABLE, "UNKNOWN CLIENT");
        }
        if(exchange.getRequest().getMethod()!= HttpMethod.GET && !validateMediaType(headers)){
            return write(exchange, HttpStatus.UNSUPPORTED_MEDIA_TYPE, "UNSUPPORTED Content-Type");
        }
        long length = headers.getContentLength();
        if(length>MAX_PACKAGE_SIZE){
            // 请求数据包过大
            return write(exchange, HttpStatus.PAYLOAD_TOO_LARGE, "PAYLOAD TOO LARGE");
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return ORDER;
    }

    /**
     * 请求头MediaType验证
     * @param headers 请求头内容
     * @return 非法返回true
     */
    private boolean validateMediaType(HttpHeaders headers){
        // 支持的请求头类型：json、xml、multipart/form-data、multipart/mixed、x-www-form-urlencoded
        MediaType contentType = headers.getContentType();
        if(contentType==null){
            return false;
        }
        return contentType.equalsTypeAndSubtype(MediaType.APPLICATION_JSON) || contentType.equalsTypeAndSubtype(MediaType.APPLICATION_STREAM_JSON)
                || contentType.equalsTypeAndSubtype(MediaType.TEXT_XML) || contentType.equalsTypeAndSubtype(MediaType.APPLICATION_XML)
                || contentType.equalsTypeAndSubtype(MediaType.TEXT_PLAIN)
                || contentType.equalsTypeAndSubtype(MediaType.MULTIPART_FORM_DATA) || contentType.equalsTypeAndSubtype(MediaType.MULTIPART_MIXED)
                || contentType.equalsTypeAndSubtype(MediaType.APPLICATION_FORM_URLENCODED);
    }

    /**
     * 验证客户端IP
     * @param headers 请求头内容
     * @return 非法返回true
     */
    private boolean validateIp(HttpHeaders headers){
        String ip = headers.getFirst(AdditionalHeaderFilter.REQUEST_IP);
        return null == ip || NetworkUtil.UNKNOWN.equalsIgnoreCase(ip);
    }

    private Mono<Void> write(ServerWebExchange exchange, HttpStatus httpStatus, String message){
        ApiResponse<String> apiResponse = ApiResponseBuilder.error(httpStatus.value(), message);

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        Mono<DataBuffer> body = Mono.just(NetworkUtil.toDataBuffer(new Gson().toJson(apiResponse).getBytes(StandardCharsets.UTF_8)));
        return response.writeWith(body);
    }
}
