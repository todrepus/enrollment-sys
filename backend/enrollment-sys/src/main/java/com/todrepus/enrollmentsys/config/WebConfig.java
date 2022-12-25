package com.todrepus.enrollmentsys.config;

import com.todrepus.enrollmentsys.web.converter.RoleConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final RoleConverter roleConverter;
    @Override
    public void addFormatters(FormatterRegistry registry){
        registry.addConverter(roleConverter);
    }
}
