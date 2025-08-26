package com.example.spring_boot_crud.security;

import com.example.spring_boot_crud.datamodel.UserRepository;
import com.example.spring_boot_crud.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.net.CookieManager;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final TokenService tokenService;
    private final CookieSupport cookies;
    private final UserRepository users;

    @Bean
    SecurityFilterChain http(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/v1/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/v1/api/users/**").permitAll()
                .anyRequest().authenticated()
        );

        http.addFilterBefore(
            new JwtCookieAuthFilter(tokenService, cookies, users),
            UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }

//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(12);
//    }
}
