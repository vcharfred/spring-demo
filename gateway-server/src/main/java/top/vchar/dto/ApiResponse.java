package top.vchar.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * <p> 接口响应 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/9/2
 */
@Builder
@Data
public class ApiResponse implements Serializable {

    private String code;

    private String message;
}
