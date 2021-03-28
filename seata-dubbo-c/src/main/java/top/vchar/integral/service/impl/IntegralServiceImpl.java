package top.vchar.integral.service.impl;

import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import top.vchar.integral.service.IntegralService;
import top.vchar.user.mapper.MemberMapper;

/**
 * <p> 积分业务接口实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/3/27
 */
@DubboService
public class IntegralServiceImpl implements IntegralService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public boolean deductIntegral(Long id, int integral) {
        int count = memberMapper.deductIntegral(id, integral);
        return count == 1;
    }
}
