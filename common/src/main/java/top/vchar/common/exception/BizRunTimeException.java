package top.vchar.common.exception;

import top.vchar.common.response.ApiCode;

/**
 * <p> 业务运行时异常 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/12
 */
public class BizRunTimeException extends RuntimeException {

    /**
     * 默认响应状态码
     */
    private int code = ApiCode.SERVER_ERROR;

    public BizRunTimeException(String message) {
        super(message);
    }

    public BizRunTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizRunTimeException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
