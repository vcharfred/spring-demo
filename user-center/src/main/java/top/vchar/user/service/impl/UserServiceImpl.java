package top.vchar.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.vchar.user.dto.UserDetailDTO;
import top.vchar.user.entity.UserInfo;
import top.vchar.user.mapper.UserInfoMapper;
import top.vchar.user.service.IUserService;

/**
 * <p> 用户相关业务接口实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserService {

    /**
     * 通过ID查询用户
     *
     * @param id 用户ID
     * @return 返回结果
     */
    @Override
    public UserDetailDTO findById(Integer id) {
        UserInfo userInfo = this.getById(id);
        if(userInfo!=null){
            UserDetailDTO userDetailDTO = new UserDetailDTO();
            BeanUtils.copyProperties(userInfo, userDetailDTO);
            return userDetailDTO;
        }
        return null;
    }
}
