package top.vchar.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.vchar.entity.Member;
import top.vchar.mapper.MemberMapper;
import top.vchar.service.MemberService;

/**
 * <p>  TODO 功能描述 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/16
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Override
    public String findNameById(Integer id) {
        Member member = this.getById(id);
        if (member != null) {
            return member.getUserName();
        }
        return null;
    }
}
