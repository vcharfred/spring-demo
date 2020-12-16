package top.vchar.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import top.vchar.user.entity.UserInfo;

/**
 * <p> 用户信息业务接口 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2020/8/31
 */
public interface UserService extends IService<UserInfo> {

    /**
     * 通过用户ID查询用户信息
     * @param id 用户ID
     * @return 返回用户名称
     */
    String findById(Long id);

    /**
     * 添加用户
     * @param userInfo 用户信息
     * @return 结果
     */
    String addUser(UserInfo userInfo);
}
