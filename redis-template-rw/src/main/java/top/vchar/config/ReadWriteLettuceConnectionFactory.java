package top.vchar.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p> 读写分离工厂 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/1/27
 */
@Slf4j
@Component
public class ReadWriteLettuceConnectionFactory implements DisposableBean {

    private final LettuceConnectionFactory writeConnectionFactory;

    private final List<LettuceConnectionFactory> readConnectionFactories = new ArrayList<>();

    private final AtomicInteger pos = new AtomicInteger();

    private int max = -1;

    public ReadWriteLettuceConnectionFactory(RedisReadWriteConfig readWriteConfig) {
        this.writeConnectionFactory = createLettuceConnectionFactory(readWriteConfig, readWriteConfig.buildWriteRedisStandaloneConfiguration());
        Assert.notNull(writeConnectionFactory, "redis config can not null, if don't used please remove dependence redis jar.");

        if (readWriteConfig.enableReadWriteModel()) {
            List<RedisStandaloneConfiguration> list = readWriteConfig.buildReadRedisStandaloneConfiguration();
            if (null != list) {
                for (RedisStandaloneConfiguration rsc : list) {
                    LettuceConnectionFactory connectionFactory = createLettuceConnectionFactory(readWriteConfig, rsc);
                    if (connectionFactory != null) {
                        log.info("redis-read-datasource - load a datasource [{}:{}] success!", rsc.getHostName(), rsc.getPort());
                        readConnectionFactories.add(connectionFactory);
                        max++;
                    } else {
                        log.warn("redis-read-datasource - load a datasource [{}:{}] fail!", rsc.getHostName(), rsc.getPort());
                    }
                }
            } else {
                log.warn("found read redis config, but can't load a datasource fail!");
            }
        }
    }

    /**
     * 获取读连接池
     *
     * @return 返回连接工厂
     */
    public LettuceConnectionFactory getReadFactory() {
        // 简单的负载均衡：轮询方案
        if (pos.get() > max) {
            pos.set(0);
        }
        int index = pos.getAndIncrement();
        log.info("chose redis-read-datasource index is [{}]", pos);
        return getReadFactory(index);
    }

    private LettuceConnectionFactory getReadFactory(int index) {
        if (index > max) {
            log.warn("no suitable redis-read-datasource [{}], the max {}.", index, max);
            // 发生错误自动切换到写连接上去
            return writeConnectionFactory;
        }
        return readConnectionFactories.get(index);
    }

    /**
     * 获取写连接池
     *
     * @return 返回连接工厂
     */
    public LettuceConnectionFactory getWriteFactory() {
        return writeConnectionFactory;
    }

    /**
     * 创建Lettuce连接工厂
     *
     * @param readWriteConfig              redis配置
     * @param redisStandaloneConfiguration redis独立配置
     * @return 返回连接工厂
     */
    private LettuceConnectionFactory createLettuceConnectionFactory(RedisReadWriteConfig readWriteConfig
            , RedisStandaloneConfiguration redisStandaloneConfiguration) {

        if (redisStandaloneConfiguration == null) {
            return null;
        }

        // 连接池配置
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        // 连接池中的最小空闲连接
        poolConfig.setMinIdle(readWriteConfig.getMinIdle());
        // 连接池中的最大空闲连接
        poolConfig.setMaxIdle(readWriteConfig.getMaxIdle());
        // 连接池最大连接数（使用负值表示没有限制,不要配置过大，否则可能会影响redis的性能）
        poolConfig.setMaxTotal(readWriteConfig.getMaxActive());
        // 连接池最大阻塞等待时间（使用负值表示没有限制）
        poolConfig.setMaxWaitMillis(readWriteConfig.getMaxWait());

        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder lettucePoolingClientConfigurationBuilder
                = LettucePoolingClientConfiguration.builder();
        // 连接池配置
        lettucePoolingClientConfigurationBuilder.poolConfig(poolConfig);
        // 关闭超时时间，单位毫秒
        lettucePoolingClientConfigurationBuilder.shutdownTimeout(Duration.ofMillis(readWriteConfig.getShutdownTimeout()));
        // 超时时间，单位毫秒
        lettucePoolingClientConfigurationBuilder.commandTimeout(Duration.ofMillis(readWriteConfig.getTimeout()));

        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration
                , lettucePoolingClientConfigurationBuilder.build());
        lettuceConnectionFactory.afterPropertiesSet();
        return lettuceConnectionFactory;
    }

    @Override
    public void destroy() throws Exception {
        writeConnectionFactory.destroy();
        if (!readConnectionFactories.isEmpty()) {
            for (LettuceConnectionFactory connectionFactory : readConnectionFactories) {
                connectionFactory.destroy();
            }
            readConnectionFactories.clear();
        }
        log.info("redis-datasource all closed success.");
    }
}
