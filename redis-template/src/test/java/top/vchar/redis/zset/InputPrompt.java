package top.vchar.redis.zset;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;

/**
 * <p> 输入框智能提示 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/3/18
 */
public class InputPrompt {

    private final RedisTemplate<String, String> redisTemplate;

    public InputPrompt(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 构建词库
     */
    public void buildPrompt(String keyWord) {
        char[] chars = keyWord.toCharArray();
        ZSetOperations<String, String> opsForZSet = redisTemplate.opsForZSet();
        StringBuilder sb = new StringBuilder("");
        for (char ch : chars) {
            sb.append(ch);
            opsForZSet.incrementScore("potential:" + sb.toString(), keyWord, System.currentTimeMillis());
        }
    }

    /**
     * 搜索
     */
    public Set<String> search(String keyWord) {
        ZSetOperations<String, String> opsForZSet = redisTemplate.opsForZSet();
        return opsForZSet.reverseRange("potential:" + keyWord, 0, 4);
    }

}
