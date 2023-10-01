package com.dromakin.springbootldap.config.server;

import com.dromakin.springbootldap.models.ldap.Permission;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "resource-server-rest-api";
    private static final String SECURED_READ_SCOPE = "#oauth2.hasScope('read')";
    private static final String SECURED_WRITE_SCOPE = "#oauth2.hasScope('write')";
    private static final String SECURED_PATTERN = "/api/**";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
                .antMatchers(SECURED_PATTERN).and().authorizeRequests()
                .antMatchers(HttpMethod.POST, SECURED_PATTERN).access(SECURED_WRITE_SCOPE)
                .anyRequest().access(SECURED_READ_SCOPE);

//        http.authorizeRequests()
//                .antMatchers("/actuator/**", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**").permitAll()
//                .anyRequest().authenticated();

//        http
//                .authorizeRequests()
//                .antMatchers("/api/**").authenticated()
//                .antMatchers("/").permitAll();

//        http
//            .authorizeRequests(authorizeRequests ->
//                    authorizeRequests
//                            .antMatchers(HttpMethod.POST, "/oauth/*").authenticated()
//            )
//            .csrf().disable()
//            .cors().disable()
//            .build();


        //        http
//            .authorizeRequests(authorizeRequests ->
//                    authorizeRequests
//                            .antMatchers(HttpMethod.POST, "/oauth/token").authenticated()
//                            .antMatchers(HttpMethod.POST, "/oauth/token_key").authenticated()
//                            .antMatchers(HttpMethod.POST, "/oauth/check_token").authenticated()
//                            .antMatchers(HttpMethod.GET, "/api/private").authenticated()
//                            .antMatchers(HttpMethod.GET, "/api/person").authenticated()
//                            .antMatchers(HttpMethod.GET, "/api/person/fullname").access(SECURED_WRITE_SCOPE)
//                            .antMatchers(HttpMethod.GET, "/api/person/name").authenticated()
//                            .antMatchers(HttpMethod.GET, "/api/person/lastname").access(SECURED_READ_SCOPE)
//                            .antMatchers(HttpMethod.GET, "/api/group/name").hasAuthority(Permission.READ.name())
//                            .antMatchers(HttpMethod.GET, "/api/group/all").hasAuthority(Permission.ALL.name())
//                            .antMatchers(HttpMethod.GET, "/api/service/name").hasAuthority(Permission.READ.name())
//                            .antMatchers(HttpMethod.GET, "/api/service/all").hasAuthority(Permission.ALL.name())
//                            .antMatchers("/actuator/**", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**").permitAll()
//                            .anyRequest().authenticated()
//            )
//            .httpBasic(Customizer.withDefaults())
//            .sessionManagement(sessionManagement ->
//                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            )
//            .csrf().disable()
//            .cors().disable()
//            .build();
    }
}
