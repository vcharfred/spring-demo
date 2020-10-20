package top.vchar.security.filter;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ResolvableType;
import org.springframework.core.codec.Hints;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.multipart.FormFieldPart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.vchar.common.exception.SignException;
import top.vchar.util.NetworkUtil;

import java.util.*;

/**
 * <p> 签名验证 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/19
 */
@Slf4j
public class SignValidateWebFilter implements WebFilter {

    private final List<HttpMessageReader<?>> messageReaders;

    public SignValidateWebFilter(){
        this.messageReaders = HandlerStrategies.withDefaults().messageReaders();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        System.out.println("签名验证...");
        ServerHttpRequest request = exchange.getRequest();
        HttpMethod method = request.getMethod();
        switch (method){
            case GET:
            case POST:
            case PUT:
            case DELETE:
                if(NetworkUtil.isUploadFile(request.getHeaders().getContentType())){
                    // 文件上传请求
                    return multipartData(exchange, chain);
                }
                return chain.filter(exchange);
            default:
                return chain.filter(exchange);
        }
    }

    private static final ResolvableType MULTIPART_DATA_TYPE = ResolvableType.forClassWithGenerics(MultiValueMap.class, String.class, Part.class);

    /**
     * 文件上传请求
     * @param exchange ServerWebExchange
     * @param chain WebFilterChain
     * @return 返回响应
     */
    private Mono<Void> multipartData(ServerWebExchange exchange, WebFilterChain chain){
        return DataBufferUtils.join(exchange.getRequest().getBody()).flatMap(dataBuffer -> {
                DataBufferUtils.retain(dataBuffer);
                final Flux<DataBuffer> bodyFlux = Flux.defer(() -> Flux.just(dataBuffer.slice(0, dataBuffer.readableByteCount())));
                final ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                    @Override
                    public Flux<DataBuffer> getBody() {
                        return bodyFlux;
                    }
                };
                final ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
                return readMultipartData(mutatedExchange, chain);
        });
    }

    /**
     * 参考：org.springframework.web.reactive.function.server.DefaultServerRequestBuilder
     *      https://blog.csdn.net/qq_32743943/article/details/89926635
     * 注意：通过exchange.getMultipartData() 读取后，下面的将不能再拿到数据了；因此这里参照spring的方法重写了一个
     * @param exchange ServerWebExchange
     * @param chain WebFilterChain
     * @return 返回响应
     */
    private Mono<Void> readMultipartData(ServerWebExchange exchange, WebFilterChain chain){
        return messageReaders.stream().filter(reader -> reader.canRead(MULTIPART_DATA_TYPE, exchange.getRequest().getHeaders().getContentType())).findFirst()
                .orElseThrow(() -> new IllegalStateException("No multipart HttpMessageReader."))
                .readMono(MULTIPART_DATA_TYPE, exchange.getRequest(), Hints.none()).flatMap(resolvedBody -> {
                    MultiValueMap<String, Part> map = (MultiValueMap<String, Part>) resolvedBody;
                    Map<String, String> params = Maps.newTreeMap();
                    for (String key : map.keySet()) {
                        Part part = map.getFirst(key);
                        if(part instanceof FormFieldPart){
                            FormFieldPart formFieldPart = (FormFieldPart) part;
                            params.put(key, formFieldPart.value());
                        }else {
                            params.put(key, "");
                        }
                    }
                    System.out.println(JSONObject.toJSONString(params));
                    if(params.isEmpty()){
                        return Mono.error(new SignException("签名不正确"));
                    }
                    return chain.filter(exchange);
                }).switchIfEmpty(Mono.error(new SignException("签名不正确.")));
    }

}
