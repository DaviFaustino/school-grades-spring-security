package com.davifaustino.schoolgradesspringsecurity.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(csrf -> csrf.disable())
                            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                            .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers(HttpMethod.GET, "/report-card/student").hasRole("STUDENT")
                                .requestMatchers(HttpMethod.POST, "/authentication/register").hasRole("ADMIN")
                                .anyRequest().hasRole("TEACHER"))
                            .httpBasic(Customizer.withDefaults())
                            .build();
    }
    
}
