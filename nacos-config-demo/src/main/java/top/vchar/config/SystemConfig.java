package top.vchar.config;

import lombok.Data;

import java.io.Serializable;

/**
 * <p> 系统配置 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/16
 */
@Data
public class SystemConfig implements Serializable {

    private String name;

    private Integer age;

}
