package com.bridgeLabz.AddressBookApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // ✅ Disable CSRF for testing purposes
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",         // ✅ Allow Swagger
                                "/v3/api-docs/**",        // ✅ Allow Swagger Docs
                                "/swagger-resources/**",  // ✅ Required for Swagger
                                "/webjars/**",            // ✅ Required for Swagger
                                "/register",              // ✅ Registration route
                                "/auth/forgotPassword/**",// ✅ Forgot password route
                                "/auth/resetPassword/**"  // ✅ Reset password route
                        ).permitAll()
                        .anyRequest().authenticated() // ❗ Other requests require authentication
                )
                .formLogin(form -> form.disable())
                .httpBasic(httpBasic -> httpBasic.disable());

        return http.build();
    }
}

