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

    /**
     * 时间戳
     */
    private final Long timestamp;

    public ApiAuthenticationToken(Object principal, Long timestamp) {
        super(null);
        this.principal = principal;
        this.timestamp = timestamp;
        super.setAuthenticated(false);
    }

    public ApiAuthenticationToken(Object principal, Long timestamp, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.timestamp = timestamp;
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

    public Long getTimestamp() {
        return timestamp;
    }
}
