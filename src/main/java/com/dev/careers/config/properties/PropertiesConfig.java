package com.dev.careers.config.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * ConfigurationProperties를 적용한 클래스들을 빈으로 등록하기위함.
 *
 * @author junehee
 */

@Configuration
@EnableConfigurationProperties(value = {CacheProperties.class, SessionProperties.class})
public class PropertiesConfig {
}
