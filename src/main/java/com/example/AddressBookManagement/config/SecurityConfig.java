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
                .csrf(csrf -> csrf.disable()) // ❌ Disable CSRF for APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/h2/",
                                "/swagger-ui/",
                                "/v3/api-docs/",
                                "/auth/",
                                "/addresses"
                        ).permitAll()
                        //.anyRequest().authenticated() // ❌ Commented out to allow all requests
                        .anyRequest().permitAll()
                )
                /*
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt) // ❌ Commented out JWT security
                */
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
}
}
