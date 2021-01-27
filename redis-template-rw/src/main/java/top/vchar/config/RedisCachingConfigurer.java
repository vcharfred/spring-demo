package top.vchar.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * <p> redis读写分离配置 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/1/26
 */
@Configuration
public class RedisCachingConfigurer<K, V> extends CachingConfigurerSupport {

    /**
     * 读数据的RedisTemplate
     *
     * @param factory LettuceConnectionFactory
     */
    @Bean(name = "readRedisTemplate")
    public RedisTemplate<K, V> readRedisTemplate(ReadWriteLettuceConnectionFactory factory) {
        return redisTemplate(factory, true);
    }

    /**
     * 写数据的RedisTemplate
     *
     * @param factory LettuceConnectionFactory
     */
    @Bean(name = "writeRedisTemplate")
    public RedisTemplate<K, V> writeRedisTemplate(ReadWriteLettuceConnectionFactory factory) {
        return redisTemplate(factory, false);
    }

    private RedisTemplate<K, V> redisTemplate(ReadWriteLettuceConnectionFactory factory, boolean isRead) {
        //创建Redis缓存操作助手RedisTemplate对象
        ReadWriteRedisTemplate<K, V> template = new ReadWriteRedisTemplate<K, V>();
        template.setReadWriteConnectionFactory(factory, isRead);
        //设置key的序列化方式
        template.setKeySerializer(keySerializer());
        template.setHashKeySerializer(keySerializer());

        //将RedisTemplate的Value序列化方式由JdkSerializationRedisSerializer更换为Jackson2JsonRedisSerializer
        template.setValueSerializer(valueSerializer());
        template.setHashValueSerializer(valueSerializer());

        template.afterPropertiesSet();
        return template;
    }

    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    private RedisSerializer<Object> valueSerializer() {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);

        ObjectMapper om = new ObjectMapper();
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会抛出异常
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        //解决时间序列化问题
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.registerModule(new JavaTimeModule());

        jackson2JsonRedisSerializer.setObjectMapper(om);
        return jackson2JsonRedisSerializer;
    }
}
