package com.todrepus.enrollmentsys.config;

import com.todrepus.enrollmentsys.domain.member.StudentState;
import com.todrepus.enrollmentsys.web.converter.RoleConverter;
import com.todrepus.enrollmentsys.web.converter.StudentStateConverter;
import com.todrepus.enrollmentsys.web.interceptor.LoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public RoleConverter roleConveter(){
        return new RoleConverter();
    }

    @Bean
    public StudentStateConverter studentStateConverter() {return new StudentStateConverter(); }

    @Bean
    public LoggingInterceptor loggingInterceptor(){
        return new LoggingInterceptor();
    }

}
