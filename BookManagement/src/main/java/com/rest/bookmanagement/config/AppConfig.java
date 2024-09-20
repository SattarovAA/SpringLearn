package com.rest.bookmanagement.config;

import com.rest.bookmanagement.config.properties.AppCacheProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.util.HashMap;
import java.util.Map;

@ComponentScan("com.rest")
@Configuration
@PropertySource("classpath:config/application.yml")
@EnableCaching
@EnableConfigurationProperties(AppCacheProperties.class)
@Slf4j
public class AppConfig {
    @Bean
    @ConditionalOnProperty(prefix = "app.redis", value = "enabled", havingValue = "true")
    @ConditionalOnExpression("'${app.cache.cacheType}'.equals('redis')")
    public CacheManager redisCacheManager(AppCacheProperties appCacheProperties,
                                          LettuceConnectionFactory lettuceConnectionFactory) {
        var defaultConfig = RedisCacheConfiguration.defaultCacheConfig();
        Map<String, RedisCacheConfiguration> redisConfiguration = new HashMap<>();
        appCacheProperties.getCacheNames().forEach(cacheName ->
                redisConfiguration.put(cacheName,
                        RedisCacheConfiguration.defaultCacheConfig().entryTtl(
                                appCacheProperties
                                        .getCaches()
                                        .get(cacheName)
                                        .getExpiry()
                        )
                ));
        return RedisCacheManager.builder(lettuceConnectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(redisConfiguration)
                .build();
    }
}
