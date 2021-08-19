package com.dev.careers.config;

import com.dev.careers.config.properties.SessionProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

/**
 * Redis 설정 및 빈 등록 (application.properties 설정값 기준) Host, Port
 */

@Configuration
@RequiredArgsConstructor
@EnableRedisRepositories
public class RedisSessionConfig {

    private final SessionProperties sessionProperties;

    /**
     * Netty기반 Redis Connection Factory 빈 등록 비동기 기반으로 동작함으로 Jedis보다 성능이 좋다.
     */
    @Bean(name = "redisConnectionFactory")
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(
                sessionProperties.getHost(),
                sessionProperties.getPort());
    }
}