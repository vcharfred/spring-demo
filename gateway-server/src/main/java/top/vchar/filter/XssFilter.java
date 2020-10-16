package top.vchar.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.vchar.util.NetworkUtil;
import top.vchar.util.XssUtil;

import javax.validation.constraints.NotEmpty;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

/**
 * <p> Xss 过滤器 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/12
 */
@RefreshScope
@ConfigurationProperties("white-url.xss")
@Slf4j
@Component
public class XssFilter implements GlobalFilter, Ordered {

    public static final int ORDER = 2;

    /**
     * xss 白名单
     */
    private List<XssWhiteUrl> whiteUrls;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();
        HttpMethod method = request.getMethod();
        Assert.notNull(method, "UNKNOWN REQUEST METHOD");

        if(NetworkUtil.isUploadFile(request.getHeaders().getContentType()) || this.isWhitelist(uri.getPath(), method)){
            return chain.filter(exchange);
        }else {
            return doFilter(method, exchange, chain);
        }
    }

    /**
     * 过滤数据
     * @param method 请求方式
     * @param exchange ServerWebExchange
     * @param chain GatewayFilterChain
     * @return 返回结果
     */
    private Mono<Void> doFilter(HttpMethod method, ServerWebExchange exchange, GatewayFilterChain chain){
        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();
        URI newUri = UriComponentsBuilder.fromUri(uri)
                .replaceQuery(XssUtil.cleanXss(uri.getQuery()))
                .build(false)
                .toUri();
        switch (method){
            case GET:
                ServerHttpRequest newGetRequest = request.mutate().uri(newUri).build();
                return chain.filter(exchange.mutate().request(newGetRequest).build());
            case POST:
            case PUT:
            case DELETE:
                return DataBufferUtils.join(request.getBody()).flatMap(d -> Mono.just(Optional.of(d))).defaultIfEmpty(Optional.empty())
                        .flatMap(optional -> {

                            // 取出body中的参数
                            String bodyString = "";
                            if (optional.isPresent()) {
                                byte[] oldBytes = new byte[optional.get().readableByteCount()];
                                optional.get().read(oldBytes);
                                bodyString = new String(oldBytes, StandardCharsets.UTF_8);
                            }
                            // 执行XSS清理
                            bodyString = XssUtil.cleanXss(bodyString);

                            ServerHttpRequest newRequest = request.mutate().uri(newUri).build();

                            // 重新构造body
                            byte[] newBytes = bodyString.getBytes(StandardCharsets.UTF_8);
                            DataBuffer bodyDataBuffer = NetworkUtil.toDataBuffer(newBytes);
                            Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);

                            // 重新构造header
                            HttpHeaders headers = new HttpHeaders();
                            headers.putAll(request.getHeaders());
                            // 由于修改了传递参数，需要重新设置CONTENT_LENGTH，长度是字节长度，不是字符串长度
                            int length = newBytes.length;
                            headers.remove(HttpHeaders.CONTENT_LENGTH);
                            headers.setContentLength(length);
                            headers.set(HttpHeaders.CONTENT_TYPE, "application/json;charset=utf8");
                            // 重写ServerHttpRequestDecorator，修改了body和header，重写getBody和getHeaders方法
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

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    /**
     * 是否是白名单
     * @param url 请求地址
     * @param method 请求方式
     * @return true：是白名单
     */
    private boolean isWhitelist(String url, HttpMethod method){
        if(whiteUrls==null || whiteUrls.isEmpty()){
            return false;
        }
        long count = whiteUrls.stream()
                .filter(p->p.getMethods()==null || p.getMethods().contains(method.name()))
                .filter(p->antPathMatcher.match(p.getUrl(), url))
                .count();
        return count>0;
    }

    @Data
    @Validated
    private static class XssWhiteUrl {

        @NotEmpty
        private String url;

        private String methods;
    }

    public List<XssWhiteUrl> getWhiteUrls() {
        return whiteUrls;
    }

    public void setWhiteUrls(List<XssWhiteUrl> whiteUrls) {
        this.whiteUrls = whiteUrls;
    }
}
