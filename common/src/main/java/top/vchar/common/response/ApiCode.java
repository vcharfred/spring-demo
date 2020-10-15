package top.vchar.common.response;

/**
 * <p> 请求响应code </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/12
 */
public enum ApiCode {

    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 发生错误
     */
    ERROR(400, "操作失败"),

    /**
     * 未授权、未登陆
     */
    UNAUTHORIZED(401, "未授权"),

    /**
     * 无访问权限
     */
    NO_AUTH(403, "权限不足"),

    /**
     * 参数不正确
     */
    PARAM_ERROR(400001, "参数不正确"),

    /**
     * 服务端发生错误、异常
     */
    SERVER_ERROR(500, "服务繁忙，请稍后再试");

    private final int value;

    private final String defaultMessage;

    ApiCode(int value, String defaultMessage){
        this.value = value;
        this.defaultMessage = defaultMessage;
    }

    /**
     * Return the integer value of this api code.
     */
    public int value() {
        return value;
    }

    /**
     * Return the String value of this api defaultMessage.
     */
    public String defaultMessage() {
        return defaultMessage;
    }

    /**
     * Return the enum constant of this type with the specified numeric value.
     * @param codeValue the numeric value of the enum to be returned
     * @return the enum constant with the specified numeric value
     * @throws IllegalArgumentException if this enum has no constant for the specified numeric value
     */
    public static ApiCode valueOf(int codeValue) {
        ApiCode code = resolve(codeValue);
        if (code == null) {
            throw new IllegalArgumentException("No matching constant for [" + codeValue + "]");
        }
        return code;
    }

    /**
     * Resolve the given api code to an {@code ApiCode}, if possible.
     * @param codeValue the Api code (potentially non-standard)
     * @return the corresponding {@code ApiCode}, or {@code null} if not found
     */
    public static ApiCode resolve(int codeValue) {
        for (ApiCode code : values()) {
            if (code.value == codeValue) {
                return code;
            }
        }
        return null;
    }

    /**
     * Return a string representation of this api code.
     */
    @Override
    public String toString() {
        return this.value + " " + name();
    }

}
