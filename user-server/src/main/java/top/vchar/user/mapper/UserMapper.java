package top.vchar.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import top.vchar.user.entity.UserInfo;

/**
 * <p> 用户mapper </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/8/31
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<UserInfo> {


    @Select("select * from user_info where id=1")
    UserInfo findById(String name);

    @Select("select * from user_info")
    UserInfo findById();


}
