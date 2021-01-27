package top.vchar.config;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * <p> 重写RedisTemplate实现读写分离 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/1/27
 */
public class ReadWriteRedisTemplate<K, V> extends RedisTemplate<K, V> {

    private ReadWriteLettuceConnectionFactory readWriteConnectionFactory;

    private boolean isRead = false;

    /**
     * RedisTemplate每次执行方法时都会调用这个方法；如果只有1读1写，那么就没有必要再弄这个封装类，直接在创建的时候指定即可
     *
     * @return RedisConnectionFactory
     */
    @Override
    public RedisConnectionFactory getRequiredConnectionFactory() {
        return getFactory();
    }

    public void setReadWriteConnectionFactory(ReadWriteLettuceConnectionFactory readWriteConnectionFactory, boolean isRead) {
        this.isRead = isRead;
        this.readWriteConnectionFactory = readWriteConnectionFactory;
        setConnectionFactory(getFactory());
    }

    private RedisConnectionFactory getFactory() {
        if (this.isRead) {
            return this.readWriteConnectionFactory.getReadFactory();
        }
        return this.readWriteConnectionFactory.getWriteFactory();
    }
}
