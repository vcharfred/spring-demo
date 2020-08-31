package top.vchar.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.vchar.user.entity.UserInfo;
import top.vchar.user.mapper.UserMapper;
import top.vchar.user.service.UserService;

/**
 * <p> 用户业务接口实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/8/31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserInfo> implements UserService {

    /**
     * 通过用户ID查询用户信息
     * @param id 用户ID
     * @return 返回用户名称
     */
    @Override
    public String findById(Long id) {
        UserInfo userInfo = this.getById(id);
        if(null!=userInfo){
            return userInfo.getNickName();
        }
        return null;
    }
}
