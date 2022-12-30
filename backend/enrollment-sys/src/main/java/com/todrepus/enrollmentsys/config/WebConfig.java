package com.todrepus.enrollmentsys.config;

import com.todrepus.enrollmentsys.web.converter.RoleConverter;
import com.todrepus.enrollmentsys.web.interceptor.LoggingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final RoleConverter roleConverter;
    private final LoggingInterceptor loggingInterceptor;
    @Override
    public void addFormatters(FormatterRegistry registry){
        registry.addConverter(roleConverter);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor)
                .order(1)
                .addPathPatterns("/**");
    }
}
