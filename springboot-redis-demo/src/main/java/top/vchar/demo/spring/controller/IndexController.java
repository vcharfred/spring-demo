package top.vchar.demo.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.quartz.SimpleThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>  redis测试 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2019/7/22 21:39
 */
@RestController
public class IndexController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/redis_test")
    public String redisTest() {
        String val = redisTemplate.opsForValue().get("test:demo");
        redisTemplate.opsForValue().set("test:demo", "this is test");
        return val;
    }

}
