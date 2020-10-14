package top.vchar.security;

import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import top.vchar.common.response.ApiCode;
import top.vchar.common.response.ApiResponse;
import top.vchar.common.response.ApiResponseBuilder;

import java.nio.charset.Charset;

/**
 * <p> 未登陆时的处理 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/14
 */
public class BizServerAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        return Mono.defer(() -> Mono.just(exchange.getResponse()))
                .flatMap(response -> {
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    DataBufferFactory dataBufferFactory = response.bufferFactory();
                    ApiResponse<String> message = ApiResponseBuilder.error(ApiCode.UNAUTHORIZED, "无效的认证信息");
                    DataBuffer buffer = dataBufferFactory.wrap(JSONObject.toJSONString(message).getBytes(Charset.defaultCharset()));
                    return response.writeWith(Mono.just(buffer))
                            .doOnError(error -> DataBufferUtils.release(buffer));
                });
    }
}
