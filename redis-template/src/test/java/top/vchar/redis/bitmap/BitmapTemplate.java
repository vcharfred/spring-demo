package top.vchar.redis.bitmap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import top.vchar.RedisApplication;

/**
 * <p> 位图：也就是二进制 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/3/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApplication.class)
public class BitmapTemplate {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 使用bitmap实现用户访问操作日志的统计功能
     */
    @Test
    public void bitmapDemo() {
        UserOperaLog userOperaLog = new UserOperaLog(this.redisTemplate);
        userOperaLog.addLog("/mall/goods/add", 1L);
        userOperaLog.addLog("/mall/goods/add", 2L);
        System.out.println(userOperaLog.hasOpera("/mall/goods/add", 1L));
    }
}
