package top.vchar.security;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import top.vchar.common.response.ApiCode;
import top.vchar.common.response.ApiResponse;
import top.vchar.common.response.ApiResponseBuilder;
import top.vchar.security.bean.AccessInfo;

import java.nio.charset.Charset;

/**
 * <p> 没有访问权限时的响应 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/14
 */
@Slf4j
public class BizServerAccessDeniedHandler implements ServerAccessDeniedHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException denied) {

        return Mono.defer(() -> Mono.just(exchange.getResponse()))
                .doOnNext(response->log.info(AccessInfo.builder(exchange).message("权限不足，请联系管理员").build().toString()))
                .flatMap(response -> {
                    response.setStatusCode(HttpStatus.FORBIDDEN);
                    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                    DataBufferFactory dataBufferFactory = response.bufferFactory();
                    ApiResponse<String> message = ApiResponseBuilder.error(ApiCode.NO_AUTH, "权限不足，请联系管理员");
                    DataBuffer buffer = dataBufferFactory.wrap(JSONObject.toJSONString(message).getBytes(Charset.defaultCharset()));
                    return response.writeWith(Mono.just(buffer))
                            .doOnError(error -> DataBufferUtils.release(buffer));
                });
    }



}
