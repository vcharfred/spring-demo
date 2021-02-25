package top.vchar.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * <p> redis连接器 </p>
 *
 * @author vchar fred
 * @version 1.0
 * @create_date 2021/2/24
 */
public class RedisHelper {

    private static RedisClient client;
    private StatefulRedisConnection<String, String> connection;

    public RedisHelper() {
        RedisURI redisUri = RedisURI.builder()
                .withHost("127.0.0.1")
                .withPort(6379)
                .withDatabase(0)
                .withTimeout(Duration.of(60, ChronoUnit.SECONDS))
                .build();
        client = RedisClient.create(redisUri);
        connection = client.connect();
    }

    public void destroy() {
        connection.close();
        client.shutdown();
    }

    public StatefulRedisConnection<String, String> getConnection() {
        return connection;
    }
}
