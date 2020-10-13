package top.vchar.common.response;

/**
 * <p> 接口响应构造器 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/12
 */
public class ApiResponseBuilder {

    private ApiResponseBuilder(){}

    /**
     * 成功的响应
     * @param message 提示信息
     * @param <T> T
     * @return 返回响应对象
     */
    public static <T> ApiResponse<T> success(String message){
        return build(ApiCode.SUCCESS, message, null);
    }

    /**
     * 成功的响应
     * @param message 提示信息
     * @param <T> T
     * @return 返回响应对象
     */
    public static <T> ApiResponse<T> success(String message, T data){
        return build(ApiCode.SUCCESS, message, data);
    }

    /**
     * 失败的响应
     * @param code 响应code
     * @param message 提示信息
     * @param <T> T
     * @return 返回响应对象
     */
    public static <T> ApiResponse<T> error(int code, String message){
        return build(code, message, null);
    }

    /**
     * 构建响应
     * @param code 响应code
     * @param message 提示信息
     * @param data 响应数据
     * @param <T> T
     * @return 返回响应对象
     */
    public static <T> ApiResponse<T> build(int code, String message, T data){
        return new ApiResponse<T>(code, message, data);
    }

}
