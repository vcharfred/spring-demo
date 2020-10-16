package top.vchar.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.vchar.security.filter.AdditionalHeaderWebFilter;
import top.vchar.util.NetworkUtil;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * <p> 请求日志打印 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/9/1
 */
@Slf4j
@Component
public class AccessLogFilter implements GlobalFilter, Ordered {

    public static final int ORDER = XssFilter.ORDER+1;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();
        String url = uri.getPath();
        HttpMethod method = request.getMethod();
        if(null==method){
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.METHOD_NOT_ALLOWED);
            return Mono.empty();
        }

        HttpHeaders headers = request.getHeaders();
        MediaType mediaType = headers.getContentType();


        String requestId = headers.getFirst(AdditionalHeaderWebFilter.REQUEST_TRACE_ID);
        String ip = headers.getFirst(AdditionalHeaderWebFilter.REQUEST_IP);

        switch (method){
            case GET:
                log.info("traceId={}, [IP:{}], [{}:{}], params:{}", requestId, ip, method, url, uri.getQuery());
                return chain.filter(exchange);
            case PUT:
            case POST:
            case DELETE:
                if(NetworkUtil.isUploadFile(mediaType)){
                    // 文件上传
                    log.info("traceId={}, [IP:{}], [{}:{}], params: upload file request", requestId, ip, method, url);
                    return chain.filter(exchange);
                }
                return DataBufferUtils.join(request.getBody())
                        .flatMap(d -> Mono.just(Optional.of(d))).defaultIfEmpty(Optional.empty()).flatMap(optional -> {
                            // 取出body中的参数
                            String bodyString = "";
                            if (optional.isPresent()) {
                                byte[] oldBytes = new byte[optional.get().readableByteCount()];
                                optional.get().read(oldBytes);
                                bodyString = new String(oldBytes, StandardCharsets.UTF_8);
                            }
                            log.info("traceId={}, [IP:{}], [{}:{}], params:{}", requestId, ip, method, url, bodyString);

                            ServerHttpRequest newRequest = request.mutate().uri(uri).build();
                            byte[] newBytes = bodyString.getBytes(StandardCharsets.UTF_8);
                            DataBuffer bodyDataBuffer = NetworkUtil.toDataBuffer(newBytes);
                            Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);

                            newRequest = new ServerHttpRequestDecorator(newRequest) {
                                @Override
                                public Flux<DataBuffer> getBody() {
                                    return bodyFlux;
                                }
                                @Override
                                public HttpHeaders getHeaders() {
                                    return headers;
                                }
                            };
                            return chain.filter(exchange.mutate().request(newRequest).build());
                        });
            default:
                return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return ORDER;
    }

}
