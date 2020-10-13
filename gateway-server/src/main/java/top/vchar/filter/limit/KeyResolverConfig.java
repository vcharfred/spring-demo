package top.vchar.filter.limit;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * <p> uri地址限流配置 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/13
 */
@Configuration
public class KeyResolverConfig {

    @Bean
    public KeyResolver uriKeyResolver(){
        return exchange->Mono.just(exchange.getRequest().getURI().getPath());
    }
}
