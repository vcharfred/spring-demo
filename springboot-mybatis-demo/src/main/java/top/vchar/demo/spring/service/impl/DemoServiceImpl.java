package top.vchar.demo.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.vchar.demo.spring.entity.User;
import top.vchar.demo.spring.mapper.DemoMapper;
import top.vchar.demo.spring.service.DemoService;

/**
 * <p>  TODO 功能描述 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/17 23:36
 */
@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoMapper demoMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)//开启事务，设置事务级别
    public int addUser(User user) {
        int num = demoMapper.insert(user);
        if(num>0){
            System.out.println("添加成功");
            return user.getId();
        }
        System.out.println("添加失败");
        return -1;
    }
}
