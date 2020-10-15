package top.vchar.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * <p> Security 配置 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/13
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    /**
     * Security 配置信息
     * @param httpSecurity ServerHttpSecurity
     * @return 返回配置对象
     */
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity httpSecurity
            , AccessReactiveAuthorizationManager accessManager, TokenReactiveAuthenticationManager tokenManager){

        AuthenticationWebFilter authenticationWebFilter = new AuthenticationWebFilter(tokenManager);
        authenticationWebFilter.setServerAuthenticationConverter(new TokenServerAuthenticationConverter());

        return httpSecurity.csrf().disable()
                .httpBasic().disable() // 关闭自带的简单认证
                .formLogin().disable() // 关闭登陆界面
                .cors().configurationSource(corsConfigurationSource()).and()
                .exceptionHandling()
                    .accessDeniedHandler(new BizServerAccessDeniedHandler())
                    .authenticationEntryPoint(new BizServerAuthenticationEntryPoint()).and()
                .addFilterAt(authenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .authorizeExchange()
                    // OPTIONS请求不拦截
                    .pathMatchers(HttpMethod.OPTIONS).permitAll()
                    // 设置自定义的授权策略
                    .anyExchange().access(accessManager).and()
                .build();
    }

    /**
     * 跨域配置
     * @return 返回跨域配置
     */
    private CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod(HttpMethod.GET);
        corsConfiguration.addAllowedMethod(HttpMethod.OPTIONS);
        corsConfiguration.addAllowedMethod(HttpMethod.POST);
        corsConfiguration.addAllowedMethod(HttpMethod.PUT);
        corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
        UrlBasedCorsConfigurationSource corsConfig = new UrlBasedCorsConfigurationSource();
        corsConfig.registerCorsConfiguration("/**", corsConfiguration);
        return corsConfig;
    }

}
