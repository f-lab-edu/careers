package com.dev.careers.config.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * Value애노테이션을 ConfigurationProperties변경
 * properties에 설정한 값을 @Value 로 가져올 수 있지만 @ConfigurationProperties를 사용하여
 * prefix를 지정함으로써 반복되는 코드를 줄일 수 있다.
 * 또한 properties에 설정값과 @Value의 설정값이 동일해야 하는 실수를 방지할 수 있다.
 * ConstructorBinding를 사용하여 final로 설정한 속성들을 생성자로 받아 불변성을 제공할 수 있습니다.
 *
 * @author junehee
 */
@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "spring.redis")
public class SessionProperties {

    private final String host;

    private final int port;
}
