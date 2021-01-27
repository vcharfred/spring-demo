package top.vchar.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p> redis读写分离配置信息 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/1/27
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisReadWriteConfig implements Serializable {

    /**
     * 主机地址
     */
    @Value("${spring.redis.host}")
    private String host;

    /**
     * 认证密码
     */
    @Value("${spring.redis.password:#{null}}")
    private String password;

    /**
     * 端口号
     */
    @Value("${spring.redis.port:6379}")
    private int port = 6379;

    /**
     * 数据库编号
     */
    @Value("${spring.redis.database:0}")
    private int database;

    /**
     * 连接超时时间，单位毫秒
     */
    @Value("${spring.redis.timeout:3000}")
    private long timeout;

    /**
     * 关闭超时时间，单位毫秒
     */
    @Value("${spring.redis.lettuce.shutdown-timeout:200}")
    private long shutdownTimeout;

    /**
     * 连接池中的最小空闲连接
     */
    @Value("${spring.redis.lettuce.pool.min-idle:1}")
    private int minIdle;

    /**
     * 连接池中的最大空闲连接
     */
    @Value("${spring.redis.lettuce.pool.max-idle:6}")
    private int maxIdle = 6;

    /**
     * 连接池最大连接数（使用负值表示没有限制,不要配置过大，否则可能会影响redis的性能）
     */
    @Value("${spring.redis.lettuce.pool.max-active:10}")
    private int maxActive = 10;

    /**
     * 连接池最大阻塞等待时间（使用负值表示没有限制），单位毫秒
     */
    @Value("${spring.redis.lettuce.pool.max-wait:1000}")
    private long maxWait = 1000;

    /**
     * redis只读库配置
     */
    private List<RedisReadConfig> redisReadConfigs;

    @Data
    @Validated
    @AllArgsConstructor
    @NoArgsConstructor
    static class RedisReadConfig {

        /**
         * 主机地址
         */
        @NotEmpty
        private String host;

        /**
         * 认证密码
         */
        private String password;

        /**
         * 端口号
         */
        private int port = 6379;

        /**
         * 数据库编号
         */
        private int database = 0;
    }

    /**
     * 只读实例配置
     *
     * @return 返回所有数据读取的配置
     */
    public List<RedisStandaloneConfiguration> buildReadRedisStandaloneConfiguration() {
        if (enableReadWriteModel()) {
            redisReadConfigs = redisReadConfigs.stream().distinct().collect(Collectors.toList());
            List<RedisStandaloneConfiguration> list = new ArrayList<>(redisReadConfigs.size());
            for (RedisReadConfig readConfig : redisReadConfigs) {
                list.add(createRedisStandaloneConfiguration(readConfig));
            }
            return list;
        }
        return null;
    }

    /**
     * 只写实例配置
     *
     * @return 返回所有数据读取的配置
     */
    public RedisStandaloneConfiguration buildWriteRedisStandaloneConfiguration() {
        RedisReadConfig redisServerConfig = new RedisReadConfig();
        redisServerConfig.setHost(this.host);
        redisServerConfig.setPort(this.port);
        redisServerConfig.setPassword(this.password);
        redisServerConfig.setDatabase(this.database);
        return createRedisStandaloneConfiguration(redisServerConfig);
    }

    /**
     * 是否启动读写分离模式
     *
     * @return 启用返回true；否则false
     */
    public boolean enableReadWriteModel() {
        return redisReadConfigs != null && !redisReadConfigs.isEmpty();
    }

    private RedisStandaloneConfiguration createRedisStandaloneConfiguration(RedisReadConfig redisServerConfig) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        // 连接地址
        redisStandaloneConfiguration.setHostName(redisServerConfig.getHost());
        // 认证密码
        redisStandaloneConfiguration.setPassword(redisServerConfig.getPassword());
        // 端口号，默认6379
        redisStandaloneConfiguration.setPort(redisServerConfig.getPort());
        // 数据库编号
        redisStandaloneConfiguration.setDatabase(redisServerConfig.getDatabase());
        return redisStandaloneConfiguration;
    }

}
