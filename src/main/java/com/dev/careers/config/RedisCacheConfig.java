package com.dev.careers.config;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis 캐시 빈 설정 redis Session, cache를 같은 타입인 RedisConnectionFactory빈이 두개이상 존재함에 따라 Qualifier를 사용하여
 * 주입받을 빈을 구분하여 처리함. 또한 redis 세션과 캐시 서버를 별도로 구성함에 따라 서버별 설정정보 설정 entryTtl 설정값을 사용하여 캐시값이 ttl 시간이 초과
 * 시 삭제되도록 설정
 *
 * @author junehee
 */
@Configuration
public class RedisCacheConfig {

    @Value("${spring.redis.cache.host}")
    private String host;

    @Value("${spring.redis.cache.port}")
    private int port;

    @Value("${spring.redis.cache.ttl}")
    private int cacheTtl;

    @Bean(name = "redisCacheConnectionFactory")
    public RedisConnectionFactory redisCacheConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    /**
     * Redis 캐시를 사용하기위해 Redis cache Manager 빈 등록
     */
    @Bean
    public CacheManager redisCacheManager(
        @Qualifier("redisCacheConnectionFactory") RedisConnectionFactory redisConnectionFactory) {

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration
            .defaultCacheConfig()
            .serializeKeysWith(RedisSerializationContext.SerializationPair
                .fromSerializer(new StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair
                .fromSerializer(new GenericJackson2JsonRedisSerializer()))
            .entryTtl(Duration.ofDays(cacheTtl));

        return RedisCacheManager.RedisCacheManagerBuilder
            .fromConnectionFactory(redisConnectionFactory)
            .cacheDefaults(redisCacheConfiguration).build();
    }

    /**
     * 자바기반 소스를 Json형식으로 직렬화 하기위해 사용한다.
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(
        @Qualifier("redisCacheConnectionFactory") RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}
