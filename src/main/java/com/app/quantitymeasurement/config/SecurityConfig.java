package com.app.quantitymeasurement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/*
 * =========================================================
 * OpenAPI / Swagger Configuration
 * =========================================================
 *
 * UC17 – API Documentation Layer
 *
 * Purpose:
 * Configures OpenAPI (Swagger) documentation for the
 * Quantity Measurement REST APIs.
 *
 * Responsibilities:
 * - Provide API metadata (title, version, description)
 * - Enable Swagger UI
 * - Allow developers to explore REST endpoints
 *
 * Access URLs:
 *
 * Swagger UI:
 * http://localhost:8080/swagger-ui.html
 *
 * OpenAPI JSON:
 * http://localhost:8080/v3/api-docs
 *
 * Benefits:
 * - Interactive API testing
 * - Automatic API documentation
 * - Developer-friendly interface
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/h2-console/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/api-docs/**",
                                "/actuator/**"
                        ).permitAll()
                        .anyRequest().permitAll())
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        return http.build();
    }
}
