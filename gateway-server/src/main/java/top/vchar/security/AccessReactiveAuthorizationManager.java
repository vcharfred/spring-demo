package top.vchar.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * <p> 路由访问管理器 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/14
 */
@Slf4j
@Component
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
        return authentication.map(p->new AuthorizationDecision(true))
                .defaultIfEmpty(new AuthorizationDecision(false));
    }
}
