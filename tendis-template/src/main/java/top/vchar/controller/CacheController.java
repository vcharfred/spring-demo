package top.vchar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> 缓存测试 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/1/14
 */
@RestController
public class CacheController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 缓存测试
     * @param key 键
     * @return 返回结果
     */
    @GetMapping("/cache")
    public String cache(@RequestParam("key") String key, String del){
        if(del!=null){
            stringRedisTemplate.delete(key);
            return "del";
        }

        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        String val = operations.get(key);
        if(val==null){
            val = "cache";
            System.out.println("更新缓存");
            operations.set(key, val);
        }
        return val;
    }

}
