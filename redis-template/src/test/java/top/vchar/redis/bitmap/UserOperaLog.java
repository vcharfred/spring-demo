package top.vchar.redis.bitmap;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * <p> 用户操作日志 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/3/18
 */
public class UserOperaLog {

    private final RedisTemplate<String, String> redisTemplate;


    public UserOperaLog(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 添加操作日志
     *
     * @param opera 操作
     * @param uid   用户ID
     */
    public void addLog(String opera, Long uid) {
        redisTemplate.opsForValue().setBit("opera_log:" + opera, uid, true);
    }

    /**
     * 判断是否存在某个key
     */
    public boolean hasOpera(String opera, Long uid) {
        Boolean res = redisTemplate.opsForValue().getBit("opera_log:" + opera, uid);
        return res != null && res;
    }

}
