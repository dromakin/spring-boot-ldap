package com.dromakin.springbootldap.config.security;

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
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers(HttpMethod.GET, "/api/person").authenticated()
                                .antMatchers(HttpMethod.GET, "/api/person/fullname").authenticated()
                                .antMatchers(HttpMethod.GET, "/api/person/name").authenticated()
                                .antMatchers(HttpMethod.GET, "/api/person/lastname").authenticated()
                                .antMatchers(HttpMethod.GET, "/api/private").authenticated()
                                .antMatchers(HttpMethod.GET, "/api/public").permitAll()
                                .antMatchers("/actuator/**", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**").permitAll()
                                .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .csrf().disable()
                .cors().disable()
                .build();
    }
}
