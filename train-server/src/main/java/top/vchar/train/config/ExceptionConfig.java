package top.vchar.train.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

import java.util.Collections;
import java.util.List;

/**
 * <p> Exception Config </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/13
 */
@Configuration
public class ExceptionConfig {

    /**
     * Custom exception handling registered Bean, Will be obtained directly from the container, so it can be injected directly
     */
    @Primary
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public ErrorWebExceptionHandler errorWebExceptionHandler(ObjectProvider<List<ViewResolver>> viewResolversProvider, ServerCodecConfigurer serverCodecConfigurer) {
        HandlerException handlerException = new HandlerException();
        handlerException.setViewResolvers(viewResolversProvider.getIfAvailable(Collections::emptyList));
        handlerException.setMessageWriters(serverCodecConfigurer.getWriters());
        handlerException.setMessageReaders(serverCodecConfigurer.getReaders());
        return handlerException;
    }

}
