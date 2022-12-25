package com.todrepus.enrollmentsys.config.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;


@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig{
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.debug("일단 스프링 시큐리티 모두 통과하도록 하였음.");
        http
                .csrf().disable()
                .headers().frameOptions().disable() // h2-coonsole 화면을 사용하기 위해 해당 옵션들을 disable
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/", "/css/**", "/images/**",
                        "/js/**", "/h2-console/**").permitAll()
                .anyRequest()
                .permitAll();
//                .authenticated()
//                .and()
//                .logout()
//                .logoutSuccessUrl("/")
//                .and()
//                .oauth2Login()
//                .userInfoEndpoint()
//                .userService(customOAuth2UserService);



        return http.build();
    }
}
