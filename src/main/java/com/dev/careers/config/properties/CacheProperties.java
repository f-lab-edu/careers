package com.dev.careers.config.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * ConfigurationProperties를 사용함으로서 개발자의 실수를 줄이고 반복적인 코드를 줄일 수 있습니다.
 * ConstructorBinding를 사용하여 생성자 파라미터로 properties의 값을 받아 camel case로 맵핑된 변수를 찾아 주입해줍니다.
 *
 * @author junehee
 */
@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "spring.redis.cache")
public class CacheProperties {

    private final String host;

    private final int port;

    private final int ttl;
}
