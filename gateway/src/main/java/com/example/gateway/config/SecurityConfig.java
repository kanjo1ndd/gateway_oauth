package com.example.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(exchanges -> exchanges
                .pathMatchers(
                    "/",
                    "/index.html",
                    "/assets/**",
                    "/static/**",
                    "/login/**",
                    "/oauth2/**",
                    "/error",
                    "/webjars/**"
                ).permitAll()
                .pathMatchers("/profile").authenticated()
                .anyExchange().authenticated()
            )
            .oauth2Login(oauth2 -> {})
            .build();
    }
}