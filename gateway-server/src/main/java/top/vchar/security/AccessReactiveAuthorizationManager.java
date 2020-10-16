package top.vchar.security;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.Part;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <p> 路由访问管理器 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/14
 */
@Slf4j
public class AccessReactiveAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    /**
     * 判断是否允许访问当前路由
     * @param authentication 授权信息
     * @param authorizationContext 授权请求上下文
     * @return 返回授权结果
     */
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext authorizationContext) {
        // TODO 判断是否有权限访问此接口地址

        // 如何防止中间人将参数值修改了
        // 签名=时间戳+随机串+参数字典序排序
//        String sign = exchange.getRequest().getHeaders().getFirst(SecurityConstant.SIGNATURE);
//        if(sign==null){
//            throw new AuthenticationCredentialsNotFoundException("");
//        }
        JSONObject params = new JSONObject();

        ServerWebExchange exchange = authorizationContext.getExchange();
        Mono<MultiValueMap<String, String>> formData = exchange.getFormData();
        Mono<MultiValueMap<String, Part>> multipartData = exchange.getMultipartData();

        ServerHttpRequest request = exchange.getRequest();
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        params.put("url", queryParams);

        System.out.println(params.toJSONString());
        System.out.println("鉴权...");
        return authentication.map(p-> new AuthorizationDecision(p.isAuthenticated()))
                .defaultIfEmpty(new AuthorizationDecision(false));
    }
}
