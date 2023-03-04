package com.todrepus.enrollmentsys.config;

import com.todrepus.enrollmentsys.web.converter.RoleConverter;
import com.todrepus.enrollmentsys.web.interceptor.AdminCheckInterceptor;
import com.todrepus.enrollmentsys.web.interceptor.LoggingInterceptor;
import com.todrepus.enrollmentsys.web.interceptor.LoginCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final RoleConverter roleConverter;
    private final LoggingInterceptor loggingInterceptor;
    private final LoginCheckInterceptor loginCheckInterceptor;
    private final AdminCheckInterceptor adminCheckInterceptor;
    @Override
    public void addFormatters(FormatterRegistry registry){
        registry.addConverter(roleConverter);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor)
                .order(1)
                .addPathPatterns("/**");
        registry.addInterceptor(loginCheckInterceptor)
                .order(2)
                .addPathPatterns("/**")
                        .excludePathPatterns(List.of("/api/auth/**", "/error"));
        registry.addInterceptor(adminCheckInterceptor)
                .order(3)
                .addPathPatterns(List.of("/api/admin/**", "/error"));
    }
}
