package top.vchar.security;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * <p> token认证信息转换器 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/14
 */
public class TokenServerAuthenticationConverter implements ServerAuthenticationConverter {

    /**
     * 这里从请求中拿到相关的认证信息，然后将组装好认证对象返回
     * @param exchange 请求对象
     * @return 返回认证信息
     */
    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        System.out.println("解析...");
        // TODO 解析请求头信息
        String sign = exchange.getRequest().getHeaders().getFirst(SecurityConstant.SIGNATURE);

        // token
        String token = exchange.getRequest().getHeaders().getFirst(SecurityConstant.ACCESS_TOKEN);
        if(null==token){
            // 请求头部没有token；从请求地址上获取
            token = exchange.getRequest().getQueryParams().getFirst(SecurityConstant.ACCESS_TOKEN);
        }

        if(StringUtils.isBlank(token)){
            return Mono.just(new AnonymousAuthenticationToken("ANONYMOUS", "dd", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS")));
        }

//        System.out.println(params.toJSONString());

//
        Authentication authentication = new ApiAuthenticationToken("admin", 110L);
        return Mono.just(authentication);
    }
}
