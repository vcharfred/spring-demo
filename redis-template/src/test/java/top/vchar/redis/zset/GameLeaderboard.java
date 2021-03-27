package top.vchar.redis.zset;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;

/**
 * <p> 排行榜 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/3/18
 */
public class GameLeaderboard {

    private final RedisTemplate<String, String> redisTemplate;

    public GameLeaderboard(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * ZADD ： 添加
     */
    public void add(String name) {
        ZSetOperations<String, String> opsForZSet = this.redisTemplate.opsForZSet();
        opsForZSet.add("game", name, 1);
    }

    /**
     * 累加操作
     */
    public void inc(String name, int incScore) {
        ZSetOperations<String, String> opsForZSet = this.redisTemplate.opsForZSet();
        opsForZSet.incrementScore("game", name, incScore);
    }


    /**
     * ZREM : 删除
     */
    public void del(String name) {
        ZSetOperations<String, String> opsForZSet = this.redisTemplate.opsForZSet();
        opsForZSet.remove("game", name);
    }

    /**
     * 获取排名
     */
    public Set<String> leaderboard() {
        ZSetOperations<String, String> opsForZSet = this.redisTemplate.opsForZSet();
        return opsForZSet.reverseRange("game", 0, 2);
    }


}
