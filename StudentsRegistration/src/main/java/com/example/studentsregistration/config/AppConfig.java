package com.example.studentsregistration.config;

import com.example.studentsregistration.StudentStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@ComponentScan("com.example.studentsregistration")
@Configuration
@PropertySource("classpath:config/application.yml")
public class AppConfig {
    @Value("${app.student.init.enabled}")
    private boolean studentsInit;
    @Value("${app.student.init.path}")
    private String inputPath;

    @Bean
    public StudentStorage studentStorage() {
        if (studentsInit && !inputPath.isEmpty()) {
            return new StudentStorage(inputPath);
        }
        return new StudentStorage();
    }
}
