package com.dromakin.springbootldap.ldap.javaconfig;

import com.dromakin.springbootldap.ldap.client.LdapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;


@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = {"com.dromakin.springbootldap.*"})
@Profile("default")
@EnableLdapRepositories(basePackages = "com.dromakin.springbootldap.**")
public class AppConfig {

    @Autowired
    private Environment env;

    @Bean
    public LdapContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl(env.getRequiredProperty("spring.ldap.urls"));
        contextSource.setBase(env.getRequiredProperty("spring.ldap.base"));
        contextSource.setUserDn(env.getRequiredProperty("spring.ldap.username"));
        contextSource.setPassword(env.getRequiredProperty("spring.ldap.password"));
        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(contextSource());
    }

    @Bean
    public LdapClient ldapClient() {
        return new LdapClient();
    }

}
