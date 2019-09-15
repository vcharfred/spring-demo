package top.vchar.demo.spring.service.impl;

import org.springframework.stereotype.Service;
import top.vchar.demo.spring.service.ConfigService;

/**
 * <p>  TODO 功能描述 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/9/3 23:28
 */
@Service
public class ConfigServiceImpl implements ConfigService {

    @Override
    public String find(String id) {
        return id+"_100";
    }
}
