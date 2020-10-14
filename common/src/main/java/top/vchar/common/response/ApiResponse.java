package top.vchar.common.response;

import lombok.Data;

import java.io.Serializable;

/**
 * <p> 接口公共响应 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/10/12
 */
@Data
public class ApiResponse<T> implements Serializable {

    private int code = ApiCode.ERROR.value();

    private String message = ApiCode.ERROR.defaultMessage();

    private T data;

    public ApiResponse(){}

    public ApiResponse(int code, String message, T data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

}
