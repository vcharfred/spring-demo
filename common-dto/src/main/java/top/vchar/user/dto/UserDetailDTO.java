package top.vchar.user.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * <p> 用户信息 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/12
 */
@Data
public class UserDetailDTO implements Serializable {

    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 用户名称
     */
    private String userName;

}
