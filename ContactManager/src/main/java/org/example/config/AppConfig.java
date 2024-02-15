package org.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@ComponentScan("org.example")
@Configuration
@PropertySource("classpath:config/application.properties")
public class AppConfig {
    public static final String NO_VALUE = "noValueInConfig";
}