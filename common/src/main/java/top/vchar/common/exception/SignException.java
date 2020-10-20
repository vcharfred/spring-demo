package top.vchar.common.exception;

import top.vchar.common.response.ApiCode;

/**
 * <p> 签名错误异常 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/19
 */
public class SignException extends RuntimeException{

    /**
     * 默认响应状态码
     */
    private int code = ApiCode.SIGN_ERROR.value();

    public SignException(String message) {
        super(message);
    }

    public SignException(String message, Throwable cause) {
        super(message, cause);
    }

    public SignException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
