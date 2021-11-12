package top.vchar.demo.spring.service.impl;

import org.springframework.stereotype.Service;
import top.vchar.demo.spring.service.AccountService;

/**
 * <p> 账号服务接口实现 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/11/12
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Override
    public boolean addAccount(Integer id, String name) {
        // TODO 添加账号
        System.out.println("账号服务研发中...");
        return false;
    }
}
