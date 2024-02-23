package com.example.contactslist.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@ComponentScan("com.example")
@Configuration
@PropertySource("classpath:config/application.yml")
public class AppConfig {
}
