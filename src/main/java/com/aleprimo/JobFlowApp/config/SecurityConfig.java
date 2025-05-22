package com.aleprimo.JobFlowApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authz -> authz

                        .requestMatchers("/api/user/**").hasRole("USER")
                        .requestMatchers("/api/application/createApplication").hasRole("USER")
                        .requestMatchers("/api/company/**").hasRole("COMPANY")
                        .requestMatchers("/api/jobs/**").hasRole("COMPANY")
                        .requestMatchers("/api/application/updateStatus/**").hasRole("COMPANY")
                        .requestMatchers("/api/application/findAllByCompanyId/**").hasRole("COMPANY")
                        .requestMatchers("/api/application/deleteById/**").hasRole("COMPANY")
                        .requestMatchers("/api/application/findById/**").hasRole("COMPANY")
                        .requestMatchers("/api/user/downloadCV/**").permitAll()
                        .anyRequest().permitAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(customJwtAuthenticationConverter())
                        )
                );

        return http.build();
    }




    @Bean
    public JwtAuthenticationConverter customJwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            List<String> roles = jwt.getClaimAsStringList("roles");
            if (roles == null) {
                return List.of();
            }

            return roles.stream()
                    .map(role -> "ROLE_" + role.toUpperCase())
                    .map(SimpleGrantedAuthority::new) .collect(Collectors.toList());
        });

        return converter;
    }




}
