package top.vchar.common.exception;

import top.vchar.common.response.ApiCode;

/**
 * <p> 业务异常 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/12
 */
public class BizException extends Exception {

    /**
     * 默认响应状态码
     */
    private int code = ApiCode.SERVER_ERROR;

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
