package top.vchar.security;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * <p> 认证管理器 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/14
 */
@Slf4j
@Component
public class TokenReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        System.out.println("认证...");
        if(authentication.getClass().isAssignableFrom(AnonymousAuthenticationToken.class)){
            authentication.setAuthenticated(false);
            return Mono.just(authentication);
        }

        // TODO 解析token中的信息，查看用户权限类型，进行授权；例如下面的

        String roles = "ROLE_PARTNER";
        if(authentication.getPrincipal()==null){
            roles = "ROLE_USER";
        }
        authentication = new ApiAuthenticationToken(authentication.getPrincipal(), System.currentTimeMillis()
                , Lists.newArrayList(new SimpleGrantedAuthority(roles)));
        return Mono.just(authentication);
    }
}
