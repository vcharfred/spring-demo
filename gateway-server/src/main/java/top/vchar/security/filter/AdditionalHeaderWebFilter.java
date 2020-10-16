package top.vchar.security.filter;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import top.vchar.common.response.ApiResponse;
import top.vchar.common.response.ApiResponseBuilder;
import top.vchar.util.NetworkUtil;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * <p> 请求头信息过滤器 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/15
 */
@Slf4j
public class AdditionalHeaderWebFilter implements WebFilter {

    public static final String REQUEST_TRACE_ID = "X-Trace-Id";

    public static final String REQUEST_IP = "X-Client-IP";

    private final List<MediaTypeMatcher> mediaTypeMatchers;

    public AdditionalHeaderWebFilter(){
        mediaTypeMatchers = Lists.newArrayList();

        mediaTypeMatchers.add(MediaTypeMatcher.build().method(HttpMethod.GET).allowNull(true)
                .addMediaTypes(MediaType.APPLICATION_FORM_URLENCODED));

        mediaTypeMatchers.add(MediaTypeMatcher.build().method(HttpMethod.DELETE).allowNull(true)
                .addMediaTypes(MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON));

        Set<MediaType> mediaTypes = Sets.newHashSet(MediaType.APPLICATION_JSON, MediaType.APPLICATION_STREAM_JSON
                , MediaType.TEXT_XML, MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN, MediaType.MULTIPART_FORM_DATA);

        mediaTypeMatchers.add(MediaTypeMatcher.build().method(HttpMethod.POST).allowNull(false).mediaTypes(mediaTypes));
        mediaTypeMatchers.add(MediaTypeMatcher.build().method(HttpMethod.PUT).allowNull(false).mediaTypes(mediaTypes));
    }

    /**
     * 请求数据包最大5M
     */
    private static final long MAX_PACKAGE_SIZE = 1024*1024*5L;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        HttpMethod method = request.getMethod();
        String ip = NetworkUtil.getIpAddress(exchange.getRequest());
        HttpHeaders headers = request.getHeaders();

        if (validateIp(ip)){
            return write(exchange, HttpStatus.NOT_ACCEPTABLE, "UNKNOWN CLIENT", ip, request.getURI().getPath(), method);
        }

        if(!validateMediaType(method, headers)){
            return write(exchange, HttpStatus.UNSUPPORTED_MEDIA_TYPE, "UNSUPPORTED Content-Type", ip, request.getURI().getPath(), method);
        }

        long length = headers.getContentLength();
        if(length>MAX_PACKAGE_SIZE){
            // 请求数据包过大
            return write(exchange, HttpStatus.PAYLOAD_TOO_LARGE, "PAYLOAD TOO LARGE", ip, request.getURI().getPath(), method);
        }

        String uuid = UUID.randomUUID().toString();
        request = request.mutate().header(REQUEST_TRACE_ID, uuid).header(REQUEST_IP, ip).build();
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set(REQUEST_TRACE_ID, uuid);
        return chain.filter(exchange.mutate().request(request).response(response).build());
    }

    /**
     * 验证客户端IP
     * @param ip 客户端IP
     * @return 非法返回true
     */
    private boolean validateIp(String ip){
        return null == ip || NetworkUtil.UNKNOWN.equalsIgnoreCase(ip);
    }

    /**
     * 请求头MediaType验证
     * @param headers 请求头内容
     * @return 非法返回true
     */
    private boolean validateMediaType(HttpMethod method, HttpHeaders headers){
        // 支持的请求头类型：json、xml、multipart/form-data、multipart/mixed、x-www-form-urlencoded
        MediaType contentType = headers.getContentType();
        return mediaTypeMatchers.stream().filter(p -> p.getMethod() == method).allMatch(p->p.matcher(contentType));
    }

    private Mono<Void> write(ServerWebExchange exchange, HttpStatus httpStatus, String message, String ip, String url, HttpMethod method){

        String requestId = UUID.randomUUID().toString();

        log.warn("traceId={}, [IP:{}], [{}:{}], error: {}", requestId, ip, method, url, message);

        ApiResponse<String> apiResponse = ApiResponseBuilder.error(httpStatus.value(), message);

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.getHeaders().set(REQUEST_TRACE_ID, requestId);
        Mono<DataBuffer> body = Mono.just(NetworkUtil.toDataBuffer(JSONObject.toJSONString(apiResponse).getBytes(StandardCharsets.UTF_8)));
        return response.writeWith(body);
    }

    static class MediaTypeMatcher {

        private boolean allowNull;

        private HttpMethod method;

        private Set<MediaType> mediaTypes;

        public MediaTypeMatcher(){
            this.mediaTypes = Sets.newHashSet();
        }

        public boolean matcher(MediaType mediaType){
            if(mediaType==null){
                return this.allowNull;
            }

            if(this.mediaTypes.isEmpty()){
                return false;
            }
            for(MediaType type:mediaTypes){
                if(type.equalsTypeAndSubtype(mediaType)){
                    return true;
                }
            }
            return false;
        }

        public HttpMethod getMethod(){
            return this.method;
        }

        public MediaTypeMatcher mediaTypes(Set<MediaType> mediaTypes){
            Assert.notNull(mediaTypes, "mediaTypes can't null");
            this.mediaTypes = mediaTypes;
            return this;
        }

        public MediaTypeMatcher addMediaTypes(MediaType ...mediaType){
            this.mediaTypes.addAll(Sets.newHashSet(mediaType));
            return this;
        }

        public MediaTypeMatcher method(HttpMethod method){
            this.method = method;
            return this;
        }

        public MediaTypeMatcher allowNull(boolean allowNull){
            this.allowNull = allowNull;
            return this;
        }

        public static MediaTypeMatcher build(){
            return new MediaTypeMatcher();
        }

    }

}
