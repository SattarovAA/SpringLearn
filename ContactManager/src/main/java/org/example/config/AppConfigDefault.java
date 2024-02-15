package org.example.config;

import org.example.ContactStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import static org.example.config.AppConfig.NO_VALUE;

@Configuration
@PropertySource("classpath:config/app-default.properties")
@Profile("default")
public class AppConfigDefault {
    @Value("${app.path.input:" + NO_VALUE + "}")
    String inputPath;

    @Bean
    public ContactStorage contactStorage() {
        return new ContactStorage(inputPath);
    }
}