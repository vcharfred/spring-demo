package top.vchar.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p> redis工具类 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/1/27
 */
@Component
public class RedisHelper {

    private final RedisTemplate<String, String> writeRedisTemplate;
    private final RedisTemplate<String, String> readRedisTemplate;

    public RedisHelper(RedisTemplate<String, String> writeRedisTemplate, RedisTemplate<String, String> readRedisTemplate) {
        this.writeRedisTemplate = writeRedisTemplate;
        this.readRedisTemplate = readRedisTemplate;
    }

    /**
     * 设置值
     *
     * @param key   缓存key
     * @param value 值
     * @param <T>   Class
     * @return 返回操作结果
     */
    public <T> boolean set(String key, T value) {
        if (value instanceof String) {
            return set(key, (String) value);
        }
        return set(key, JSON.toJSONString(value));
    }

    /**
     * 设置值
     *
     * @param key       缓存key
     * @param value     值
     * @param validTime 缓存时间，单位秒
     * @param <T>       Class
     * @return 返回操作结果
     */
    public <T> boolean set(String key, T value, long validTime) {
        if (value instanceof String) {
            return set(key, (String) value, validTime);
        }
        return set(key, JSON.toJSONString(value), validTime);
    }

    /**
     * 设置值
     *
     * @param key       缓存key
     * @param value     值
     * @param validTime 缓存时间，单位秒
     * @return 返回操作结果
     */
    private boolean set(String key, String value, long validTime) {
        Boolean res = this.writeRedisTemplate.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer<String> serializer = this.writeRedisTemplate.getStringSerializer();
            byte[] keyByte = Objects.requireNonNull(serializer.serialize(key));
            byte[] valueByte = Objects.requireNonNull(serializer.serialize(value));
            connection.set(keyByte, valueByte);
            connection.expire(keyByte, validTime);
            return true;
        });
        return res != null && res;
    }

    /**
     * 设置某个值的缓存时间
     *
     * @param key       缓存key
     * @param validTime 缓存时间，单位秒
     * @return 返回操作结果
     */
    public boolean setExpire(String key, long validTime) {
        Boolean res = this.writeRedisTemplate.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer<String> serializer = this.writeRedisTemplate.getStringSerializer();
            byte[] keyByte = Objects.requireNonNull(serializer.serialize(key));
            connection.expire(keyByte, validTime);
            return true;
        });
        return res != null && res;
    }

    /**
     * 设置值
     *
     * @param key   缓存key
     * @param value 值
     * @return 返回操作结果
     */
    private boolean set(String key, String value) {
        Boolean res = this.writeRedisTemplate.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer<String> serializer = this.writeRedisTemplate.getStringSerializer();
            connection.set(Objects.requireNonNull(serializer.serialize(key)), Objects.requireNonNull(serializer.serialize(value)));
            return true;
        });
        return res != null && res;
    }

    /**
     * 获取值
     *
     * @param key   缓存key
     * @param clazz 反序列的Class
     * @param <T>   T
     * @return 返回单个结果
     */
    public <T> T get(String key, Class<T> clazz) {
        return JSON.parseObject(getValue(key), clazz);
    }

    /**
     * 获取值
     *
     * @param key   缓存key
     * @param clazz 反序列的Class
     * @param <T>   T
     * @return 返回List
     */
    public <T> List<T> getList(String key, Class<T> clazz) {
        return JSONArray.parseArray(getValue(key), clazz);
    }

    /**
     * 获取值
     *
     * @param key 缓存key
     * @return 返回单个结果
     */
    public String get(String key) {
        return getValue(key);
    }

    /**
     * 获取值
     *
     * @param key 缓存key
     * @return 返回单个结果
     */
    private String getValue(String key) {
        return this.readRedisTemplate.execute((RedisCallback<String>) connection -> {
            RedisSerializer<String> serializer = this.readRedisTemplate.getStringSerializer();
            byte[] value = connection.get(Objects.requireNonNull(serializer.serialize(key)));
            return serializer.deserialize(value);
        });
    }

    /**
     * 删除值
     *
     * @param key 缓存key
     */
    public void del(String key) {
        this.writeRedisTemplate.delete(key);
    }

    /**
     * 批量删除相同前缀的key
     *
     * @param prefix 前缀
     */
    public void batchDel(String prefix) {
        Set<String> keys = keys(prefix);
        if (null != keys && !keys.isEmpty()) {
            this.writeRedisTemplate.delete(keys);
        }
    }

    /**
     * 批量删除
     *
     * @param keys 需要删除的key
     */
    public void batchDel(Collection<String> keys) {
        this.writeRedisTemplate.delete(keys);
    }

    /**
     * 判断值缓存key是否存在
     *
     * @param key 缓存key
     */
    public boolean exist(String key) {
        Boolean res = this.writeRedisTemplate.hasKey(key);
        return res != null && res;
    }

    /**
     * 获取相同前缀的key
     *
     * @param prefix 前缀
     */
    public Set<String> keys(String prefix) {
        return this.readRedisTemplate.keys(prefix + "*");
    }

    /**
     * 如果key不存在则设置，此方法使用了redis的原子性
     *
     * @param key       key
     * @param value     value
     * @param validTime 缓存时间; 若要是使用锁，建议设置有效时间，避免死锁
     * @return key不存在设置成功返回true; 否则返回false
     */
    public boolean setNx(String key, String value, long validTime) {
        return setNx(key, value, validTime, TimeUnit.SECONDS);
    }

    /**
     * 如果key不存在则设置，此方法使用了redis的原子性
     *
     * @param key       key
     * @param value     value
     * @param validTime 缓存时间; 若要是使用锁，建议设置有效时间，避免死锁
     * @param timeUnit  时间单位
     * @return key不存在设置成功返回true; 否则返回false
     */
    public boolean setNx(String key, String value, long validTime, TimeUnit timeUnit) {
        try {
            ValueOperations<String, String> operations = this.writeRedisTemplate.opsForValue();
            Boolean lock = operations.setIfAbsent(key, value, validTime, timeUnit);
            return lock != null && lock;
        } catch (Exception e) {
            this.del(key);
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 累加1,从0开始
     *
     * @param key       缓存key
     * @param validTime 缓存时间，单位秒
     * @return 返回增加后的值
     */
    public Long incrExpire(String key, long validTime) {
        return this.writeRedisTemplate.execute((RedisCallback<Long>) connection -> {
            RedisSerializer<String> serializer = this.writeRedisTemplate.getStringSerializer();
            byte[] keyByte = Objects.requireNonNull(serializer.serialize(key));
            Long val = connection.incr(keyByte);
            connection.expire(keyByte, validTime);
            return val;
        });
    }

    /**
     * 累加1,从0开始
     *
     * @param key 缓存key
     * @return 返回操作结果
     */
    public boolean incr(String key) {
        Boolean res = this.writeRedisTemplate.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer<String> serializer = this.writeRedisTemplate.getStringSerializer();
            connection.incr(Objects.requireNonNull(serializer.serialize(key)));
            return true;
        });
        return res != null && res;
    }

    /**
     * 累减1,从0开始
     *
     * @param key 缓存key
     * @return 返回操作结果
     */
    public boolean decr(String key) {
        Boolean res = this.writeRedisTemplate.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer<String> serializer = this.writeRedisTemplate.getStringSerializer();
            connection.decr(Objects.requireNonNull(serializer.serialize(key)));
            return true;
        });
        return res != null && res;
    }

    /**
     * 累加指定的long类型的值,从0开始
     *
     * @param key   缓存key
     * @param value 累加的值
     * @return 返回操作结果
     */
    public boolean incr(String key, long value) {
        return incr(key, value, null);
    }

    /**
     * 累减指定的long类型的值,从0开始
     *
     * @param key   缓存key
     * @param value 累减的值
     * @return 返回操作结果
     */
    public boolean decr(String key, long value) {
        return decr(key, value, null);
    }

    /**
     * 累加指定的double类型的值,从0开始
     *
     * @param key   缓存key
     * @param value 累加的值
     * @return 返回操作结果
     */
    public boolean incr(String key, double value) {
        return incr(key, null, value);
    }

    /**
     * 累减指定的double类型的值,从0开始
     *
     * @param key   缓存key
     * @param value 累减的值
     * @return 返回操作结果
     */
    public boolean decr(String key, double value) {
        return decr(key, null, value);
    }

    /**
     * 累加指定的Long或Double类型的值,从0开始；任选一个
     *
     * @param key       缓存key
     * @param valLong   累加的Long类型的值
     * @param valDouble 累加的Double类型的值
     * @return 返回操作结果
     */
    public boolean incr(String key, Long valLong, Double valDouble) {
        Boolean res = this.writeRedisTemplate.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer<String> serializer = this.writeRedisTemplate.getStringSerializer();
            if (null == valDouble) {
                connection.incrBy(Objects.requireNonNull(serializer.serialize(key)), valLong);
            } else {
                connection.incrBy(Objects.requireNonNull(serializer.serialize(key)), valDouble);
            }
            return true;
        });
        return res != null && res;
    }

    /**
     * 累减指定的Long或Double类型的值,从0开始；任选一个
     *
     * @param key       缓存key
     * @param valLong   累减的Long类型的值
     * @param valDouble 累减的Double类型的值
     * @return 返回操作结果
     */
    public boolean decr(String key, Long valLong, Double valDouble) {
        Boolean res = this.writeRedisTemplate.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer<String> serializer = this.writeRedisTemplate.getStringSerializer();
            if (null == valDouble) {
                connection.decrBy(Objects.requireNonNull(serializer.serialize(key)), valLong);
            } else {
                connection.incrBy(Objects.requireNonNull(serializer.serialize(key)), -valDouble);
            }
            return true;
        });
        return res != null && res;
    }


}
