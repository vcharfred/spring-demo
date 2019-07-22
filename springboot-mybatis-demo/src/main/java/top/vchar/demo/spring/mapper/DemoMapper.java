package top.vchar.demo.spring.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import top.vchar.demo.spring.entity.User;

/**
 * <p>  TODO 接口说明 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/17 23:36
 */
public interface DemoMapper {
    //推荐使用#{}； 不建议使用${},因为存在sql注入风险

    //keyProperty--Java对象属性，keyColumn--数据库字段
    @Insert("INSERT INTO user(name, age) VALUES(#{name}, #{age})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(User user);

}
