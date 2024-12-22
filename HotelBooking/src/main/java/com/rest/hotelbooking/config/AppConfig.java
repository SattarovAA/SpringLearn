package com.rest.hotelbooking.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Default application configuration.
 */
@ComponentScan("com.rest")
@Configuration
@PropertySource("classpath:config/application.yml")
public class AppConfig {
}
