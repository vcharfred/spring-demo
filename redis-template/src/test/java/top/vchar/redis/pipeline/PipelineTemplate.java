package top.vchar.redis.pipeline;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import top.vchar.RedisApplication;

import java.time.Duration;

/**
 * <p> 事务操作 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/3/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApplication.class)
public class PipelineTemplate {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void pipelineDemo() {
        boolean lockRes = lock("pipeline_trans", "100");
        System.out.println("加锁" + (lockRes ? "成功" : "失败"));

        lockRes = lock("pipeline_trans", "200");
        System.out.println("加锁" + (lockRes ? "成功" : "失败"));

        boolean unlockRes = unlock("pipeline_trans", "200");
        System.out.println("解锁" + (unlockRes ? "成功" : "失败"));

        unlockRes = unlock("pipeline_trans", "100");
        System.out.println("解锁" + (unlockRes ? "成功" : "失败"));
    }

    private boolean lock(String key, String val) {
        Boolean res = redisTemplate.opsForValue().setIfAbsent(key, val, Duration.ofMinutes(2));
        return res != null && res;
    }

    private boolean unlock(String key, String val) {
        String currentVal = redisTemplate.opsForValue().get(key);

        RedisOperations<String, String> operations = redisTemplate.opsForValue().getOperations();
        // 使用watch后，如果key值发生改变，则后面提交事务会失败
        operations.watch("pipeline_demo");
        if (currentVal == null || currentVal.length() == 0) {
            return true;
        }
        if (currentVal.equals(val)) {
            try {
                // 开启事务
                operations.multi();
                // 设置接下来需要执行的命令
                operations.delete("pipeline_demo");
                // 发送到redis去命令
                operations.exec();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                operations.discard();
            }
        } else {
            return false;
        }
        return true;
    }


}
