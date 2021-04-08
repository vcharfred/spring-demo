package top.vchar.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import top.vchar.user.entity.Member;

/**
 * <p> 用户mapper </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/3/27
 */
@Mapper
@Repository
public interface MemberMapper extends BaseMapper<Member> {

    /**
     * 扣除积分
     *
     * @param id       用户id
     * @param integral 扣除的积分值
     * @return 返回影响的行数
     */
    @Update("update member set integral= integral-#{integral} where id=#{id} and integral-#{integral}>=0")
    int deductIntegral(@Param("id") Long id, @Param("integral") Integer integral);
}
