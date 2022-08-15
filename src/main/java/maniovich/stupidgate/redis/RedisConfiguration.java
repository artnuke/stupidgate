package maniovich.stupidgate.redis;

import maniovich.stupidgate.transaction.Transaction;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;

@Configuration
public class RedisConfiguration {
    public static JedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName("172.17.0.2");
        redisStandaloneConfiguration.setPort(6379);

        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
        jedisClientConfiguration.connectTimeout(Duration.ofSeconds(60));// 60s connection timeout

        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory(redisStandaloneConfiguration,
                jedisClientConfiguration.build());

        return jedisConFactory;

    }
    public static RedisTemplate<String, Transaction> redisTemplate() {
        RedisTemplate<String, Transaction> redisTemplate = new RedisTemplate<String ,Transaction>();

        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }
}