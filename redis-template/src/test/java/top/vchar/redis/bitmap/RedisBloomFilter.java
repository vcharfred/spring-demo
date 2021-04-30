package top.vchar.redis.bitmap;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * <p> 布隆过滤器 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/4/22
 */
public class RedisBloomFilter<T> {

    private final RedisTemplate<String, T> redisTemplate;
    private final String key;
    private final int capacity;
    private final int numHashFunctions = 2;

    public RedisBloomFilter(String key, RedisTemplate<String, T> redisTemplate, int n, double p) {
        this.redisTemplate = redisTemplate;
        this.key = key;
        this.capacity = calculateCapacity(n, p);
    }

    public void put(T value) {
        int[] hash = index(value);
        for (int i = 1; i <= numHashFunctions; ++i) {
            int combinedHash = hash[0] + i * hash[1];
            if (combinedHash < 0) {
                combinedHash = ~combinedHash;
            }
            this.redisTemplate.opsForValue().setBit(key, combinedHash % capacity, true);
        }
    }

    public void del(T value) {
        int[] hash = index(value);
        for (int i = 1; i <= numHashFunctions; ++i) {
            int combinedHash = hash[0] + i * hash[1];
            if (combinedHash < 0) {
                combinedHash = ~combinedHash;
            }
            this.redisTemplate.opsForValue().setBit(key, combinedHash % capacity, false);
        }
    }

    public boolean mightContain(T value) {
        int[] hash = index(value);
        for (int i = 1; i <= numHashFunctions; ++i) {
            int combinedHash = hash[0] + i * hash[1];
            if (combinedHash < 0) {
                combinedHash = ~combinedHash;
            }
            Boolean exist = this.redisTemplate.opsForValue().getBit(key, combinedHash % capacity);
            if (Boolean.FALSE.equals(exist)) {
                return false;
            }
        }
        return true;
    }


    private int[] index(T value) {
        int hash1 = hash(value);
        int hash2 = hash1 ^ (hash1 >>> 16);
        return new int[]{hash1, hash2};
    }

    private static int hash(Object key) {
        return (key == null) ? 0 : key.hashCode();
    }

    private static int calculateCapacity(int n, double p) {
        if (p == 0) {
            p = Double.MIN_VALUE;
        }
        return (int) (-n * Math.log(p) / (Math.log(2) * Math.log(2)));
    }

}
