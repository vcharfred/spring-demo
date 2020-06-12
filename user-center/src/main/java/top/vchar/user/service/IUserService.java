package top.vchar.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.vchar.user.dto.UserDetailDTO;
import top.vchar.user.entity.UserInfo;

/**
 * <p> 用户相关业务接口 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/6/11
 */
public interface IUserService extends IService<UserInfo> {

    /**
     * 通过ID查询用户
     * @param id 用户ID
     * @return 返回结果
     */
    UserDetailDTO findById(Integer id);
}
