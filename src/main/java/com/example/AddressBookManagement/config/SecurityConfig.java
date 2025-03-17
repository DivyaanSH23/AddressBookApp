package com.example.AddressBookManagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // ✅ Disable CSRF for APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/h2/**",          // ✅ Allow H2 Console
                                "/swagger-ui/**",  // ✅ Allow Swagger UI
                                "/v3/api-docs/**", // ✅ Allow API Docs
                                "/auth/**"         // ✅ Allow Authentication APIs
                        ).permitAll()
                        .anyRequest().permitAll() // 🚨 Open everything for testing (change later)
                )
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())); // ✅ Allow Frames for H2

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
