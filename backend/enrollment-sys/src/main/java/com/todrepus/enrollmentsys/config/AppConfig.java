package com.todrepus.enrollmentsys.config;

import com.todrepus.enrollmentsys.domain.member.StudentState;
import com.todrepus.enrollmentsys.web.converter.RoleConverter;
import com.todrepus.enrollmentsys.web.converter.StudentStateConverter;
import com.todrepus.enrollmentsys.web.interceptor.AdminCheckInterceptor;
import com.todrepus.enrollmentsys.web.interceptor.LoggingInterceptor;
import com.todrepus.enrollmentsys.web.interceptor.LoginCheckInterceptor;
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

    @Bean
    public LoginCheckInterceptor loginCheckInterceptor() {return new LoginCheckInterceptor();}

    @Bean
    public AdminCheckInterceptor adminCheckInterceptor() {return new AdminCheckInterceptor();}

}
