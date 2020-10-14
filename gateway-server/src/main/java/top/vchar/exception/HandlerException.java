package top.vchar.exception;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.util.Assert;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import top.vchar.common.exception.BizException;
import top.vchar.common.exception.BizRunTimeException;
import top.vchar.common.response.ApiResponse;
import top.vchar.common.response.ApiResponseBuilder;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * <p> 异常解析器 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/12
 */
@Slf4j
public class HandlerException implements ErrorWebExceptionHandler {

    /**
     * MessageReader
     */
    private List<HttpMessageReader<?>> messageReaders = Collections.emptyList();

    /**
     * MessageWriter
     */
    private List<HttpMessageWriter<?>> messageWriters = Collections.emptyList();

    /**
     * ViewResolvers
     */
    private List<ViewResolver> viewResolvers = Collections.emptyList();

    /**
     * 存储处理异常后的信息
     */
    private final ThreadLocal<ErrorMessage> exceptionHandlerResult = new ThreadLocal<>();

    /**
     * 设置视消息读取器；参考AbstractErrorWebExceptionHandler
     *
     * @param messageReaders 消息读取
     */
    void setMessageReaders(List<HttpMessageReader<?>> messageReaders) {
        Assert.notNull(messageReaders, "'messageReaders' must not be null");
        this.messageReaders = messageReaders;
    }

    /**
     * 设置视图解析器；参考AbstractErrorWebExceptionHandler
     *
     * @param viewResolvers 视图解析器
     */
    void setViewResolvers(List<ViewResolver> viewResolvers) {
        this.viewResolvers = viewResolvers;
    }

    /**
     * 设置消息写入器；参考AbstractErrorWebExceptionHandler
     *
     * @param messageWriters 消息写入器
     */
    void setMessageWriters(List<HttpMessageWriter<?>> messageWriters) {
        Assert.notNull(messageWriters, "'messageWriters' must not be null");
        this.messageWriters = messageWriters;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

        // 参考AbstractErrorWebExceptionHandler
        if(exchange.getResponse().isCommitted()){
            return Mono.error(ex);
        }

        storeErrorInformation(ex);
        ServerRequest newRequest = ServerRequest.create(exchange, this.messageReaders);
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse).route(newRequest)
                .switchIfEmpty(Mono.error(ex))
                .flatMap((handler) -> handler.handle(newRequest))
                .flatMap((response) -> write(exchange, response));
    }

    /**
     * store error info to local thread
     * @param ex Throwable
     */
    private void storeErrorInformation(Throwable ex){
        ApiResponse<String> body;
        HttpStatus httpStatus;
        if (ex instanceof NotFoundException) {
            httpStatus = HttpStatus.NOT_FOUND;
            body = ApiResponseBuilder.error(httpStatus.value(), "没有找到该资源");
        } else if (ex instanceof ResponseStatusException) {
            ResponseStatusException responseStatusException = (ResponseStatusException) ex;
            httpStatus = responseStatusException.getStatus();
            body = ApiResponseBuilder.error(httpStatus.value(), responseStatusException.getMessage());
        } else if (ex instanceof BizRunTimeException) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            body = ApiResponseBuilder.error(((BizRunTimeException) ex).getCode(), ex.getMessage());
        } else if (ex instanceof BizException) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            body = ApiResponseBuilder.error(((BizException) ex).getCode(), ex.getMessage());
        } else if (ex instanceof MethodArgumentNotValidException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            Optional<ObjectError> objectErrorOptional = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors().stream().findAny();
            String message = objectErrorOptional.isPresent() ? objectErrorOptional.get().getDefaultMessage() : "参数错误";
            body = ApiResponseBuilder.error(httpStatus.value(), message);
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            body = ApiResponseBuilder.error(httpStatus.value(), ex.getMessage());
        }

        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setBody(body);
        errorMessage.setHttpStatus(httpStatus);

        exceptionHandlerResult.set(errorMessage);
    }


    /**
     * 响应错误信息
     *
     * @param request 请求信息
     * @return 请求响应
     */
    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        ErrorMessage result = exceptionHandlerResult.get();
        exceptionHandlerResult.remove();
        log.info("interface exception：{} 异常响应为: {}", request.path(), result);
        return ServerResponse.status(result.getHttpStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.getBody());
    }

    @Data
    static class ErrorMessage implements Serializable {

        private ApiResponse<String> body;

       private HttpStatus httpStatus;

       public BodyInserter<ApiResponse<String>, ReactiveHttpOutputMessage> getBody(){
           if(httpStatus==HttpStatus.NOT_FOUND){
               return BodyInserters.empty();
           }
           return BodyInserters.fromObject(body);
       }
    }

    /**
     * output response
     *
     * @param exchange ServerWebExchange
     * @param response response
     * @return return response result
     */
    private Mono<? extends Void> write(ServerWebExchange exchange, ServerResponse response) {
        exchange.getResponse().getHeaders().setContentType(response.headers().getContentType());
        return response.writeTo(exchange, new ResponseContext());
    }

    /**
     * override response context
     */
    private class ResponseContext implements ServerResponse.Context {

        @Override
        public List<HttpMessageWriter<?>> messageWriters() {
            return HandlerException.this.messageWriters;
        }

        @Override
        public List<ViewResolver> viewResolvers() {
            return HandlerException.this.viewResolvers;
        }

    }

}
