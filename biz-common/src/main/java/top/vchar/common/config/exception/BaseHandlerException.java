package top.vchar.common.config.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.vchar.common.exception.BizException;
import top.vchar.common.response.ApiCode;
import top.vchar.common.response.ApiResponse;
import top.vchar.common.response.ApiResponseBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * <p> interface exception handler </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/13
 */
@ControllerAdvice
@Slf4j
public class BaseHandlerException {

    /**
     * Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ApiResponse<String> handler(HttpServletRequest request, Exception e) {
        log.error("{} {} 异常: ", request.getMethod(), request.getRequestURL(), e);
        return ApiResponseBuilder.error(ApiCode.SERVER_ERROR, "服务繁忙，请稍后再试");
    }

    /**
     * BizException
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public ApiResponse<String> handler(HttpServletRequest request, BizException e) {
        log.error("{} {} 业务异常: ", request.getMethod(), request.getRequestURL(), e);
        return ApiResponseBuilder.error(e.getCode(), e.getMessage());
    }

    /**
     * MethodArgumentNotValidException
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiResponse<String> handler(HttpServletRequest request, MethodArgumentNotValidException e) {
        log.error("{} {} 参数验证异常: ", request.getMethod(), request.getRequestURL(), e);
        Optional<ObjectError> objectErrorOptional = e.getBindingResult().getAllErrors().stream().findAny();
        String message = objectErrorOptional.isPresent() ? objectErrorOptional.get().getDefaultMessage() : "参数错误";
        return ApiResponseBuilder.error(ApiCode.PARAM_ERROR, message);
    }

    /**
     * BindException
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public ApiResponse<String> handler(HttpServletRequest request, BindException e) {
        log.error("{} {} 参数验证或类型转换失败: ", request.getMethod(), request.getRequestURL(), e);
        Optional<ObjectError> objectErrorOptional = e.getBindingResult().getAllErrors().stream().findAny();
        String message = objectErrorOptional.isPresent() ? objectErrorOptional.get().getDefaultMessage() : "参数错误";

        // 参数验证都会有一个默认的中文提示信息，这里做一个匹配，避免将Java代码返回给前端导致代码字段泄露的问题
        boolean matches = Pattern.matches("[\\u4e00-\\u9fa5]{1,50}", message);
        if (!matches) {
            message = "参数错误";
        }
        return ApiResponseBuilder.error(ApiCode.PARAM_ERROR, message);
    }

    /**
     * HttpMessageConversionException
     */
    @ExceptionHandler(value = HttpMessageConversionException.class)
    @ResponseBody
    public ApiResponse<String> handler(HttpServletRequest request, NestedRuntimeException e) {
        log.error("{} {} 参数序列化错误: ", request.getMethod(), request.getRequestURL(), e);
        return ApiResponseBuilder.error(ApiCode.PARAM_ERROR, "数据格式有误");
    }

    /**
     * ServletException
     */
    @ExceptionHandler(value = ServletException.class)
    @ResponseBody
    public ApiResponse<String> handler(HttpServletRequest request, ServletException e) {
        log.error("{} {} 请求处理异常: ", request.getMethod(), request.getRequestURL(), e);
        return ApiResponseBuilder.error(ApiCode.PARAM_ERROR, "参数错误");
    }

    /**
     * BeansException
     */
    @ExceptionHandler(value = BeansException.class)
    @ResponseBody
    public ApiResponse<String> handler(HttpServletRequest request, BeansException e) {
        log.error("{} {} 提取参数异常: ", request.getMethod(), request.getRequestURL(), e);
        return ApiResponseBuilder.error(ApiCode.PARAM_ERROR, "参数错误");
    }

    /**
     * RuntimeException
     */
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public ApiResponse<String> handler(HttpServletRequest request, RuntimeException e) {
        log.error("{} {} 系统异常: ", request.getMethod(), request.getRequestURL(), e);
        return ApiResponseBuilder.error(ApiCode.PARAM_ERROR, "服务繁忙，请稍后再试");
    }

}
