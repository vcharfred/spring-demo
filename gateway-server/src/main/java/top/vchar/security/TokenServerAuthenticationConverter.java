package top.vchar.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

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
        // 解析请求头token
        String token = exchange.getRequest().getHeaders().getFirst(SecurityConstant.ACCESS_TOKEN);
        if(null==token){
            // 请求头部没有token；从请求地址上获取
            token = exchange.getRequest().getQueryParams().getFirst(SecurityConstant.ACCESS_TOKEN);
        }

        if(StringUtils.isBlank(token)){
            return Mono.just(new AnonymousAuthenticationToken("ANONYMOUS", "NONE", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS")));
        }
        return Mono.just(new ApiAuthenticationToken(token));
    }
}
