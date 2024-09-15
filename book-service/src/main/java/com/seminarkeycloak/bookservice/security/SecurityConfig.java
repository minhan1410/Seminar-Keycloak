package com.seminarkeycloak.bookservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    public static final String ADMIN = "Admin Role";
    public static final String USER = "User Role";
    private final JwtConverter jwtConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter)))
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers(HttpMethod.GET, "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/hello").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/admin/**").hasRole(ADMIN)
                                .requestMatchers(HttpMethod.GET, "/api/user/**").hasRole(USER)
                                .requestMatchers(HttpMethod.GET, "/api/admin-and-user/**").hasAnyRole(ADMIN, USER)
                                .anyRequest().authenticated()
                ).build();
    }
}
