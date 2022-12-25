package com.todrepus.enrollmentsys.config;

import com.todrepus.enrollmentsys.web.converter.RoleConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public RoleConverter roleConveter(){
        return new RoleConverter();
    }
}
