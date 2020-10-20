package top.vchar.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

/**
 * <p> 接口认证token </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/14
 */
public class ApiAuthenticationToken extends AbstractAuthenticationToken implements Serializable {

    /**
     * token原文信息
     */
    private final Object principal;

    public ApiAuthenticationToken(Object token) {
        super(null);
        this.principal = token;
        super.setAuthenticated(false);
    }

    public ApiAuthenticationToken(Object token, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = token;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.principal;
    }

    @Override
    public Object getPrincipal() {
        return "";
    }

    public String getToken(){
        return this.principal==null? null : (String) this.principal;
    }

}
