package top.vchar.redis;

import io.lettuce.core.api.sync.RedisCommands;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * <p> hashset的使用 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/2/24
 */
public class HashDemo {

    private RedisHelper redisHelper;

    @BeforeEach
    public void before() {
        redisHelper = new RedisHelper();
    }

    @AfterEach
    public void after() {
        redisHelper.destroy();
    }

    @Test
    public void hashDemo() {
        RedisCommands<String, String> redisCommands = redisHelper.getConnection().sync();
        // 类似 Map<Key,Value>
//        redisCommands.hset("active_ip","127.0.0.1", "0");
        redisCommands.hincrby("active_ip", "127.0.0.1", 1);
        System.out.println(redisCommands.hget("active_ip", "127.0.0.1"));

        Map<String, String> activeIp = redisCommands.hgetall("active_ip");
        System.out.println(activeIp);
    }

}
