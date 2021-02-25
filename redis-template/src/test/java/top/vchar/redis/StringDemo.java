package top.vchar.redis;

import io.lettuce.core.api.sync.RedisCommands;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * <p> 字符串类型的使用示例 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/2/24
 */
public class StringDemo {

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
    public void demo() {
        RedisCommands<String, String> redisCommands = redisHelper.getConnection().sync();
        redisCommands.set("string_demo", "1024");
        System.out.println(redisCommands.get("string_demo"));
        redisCommands.getrange("", 0, 10);
        redisCommands.del("string_demo");
    }

}
