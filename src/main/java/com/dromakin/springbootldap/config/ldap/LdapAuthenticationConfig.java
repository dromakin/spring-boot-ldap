package com.dromakin.springbootldap.config.ldap;

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.ldap.core.support.BaseLdapPathContextSource;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
//import org.springframework.security.ldap.userdetails.InetOrgPersonContextMapper;
//
//@Configuration
//@EnableWebSecurity
//public class LdapAuthenticationConfig {
//
//    @Bean
//    public AuthenticationManager ldapAuthenticationManager(BaseLdapPathContextSource contextSource) {
//        LdapBindAuthenticationManagerFactory factory = new LdapBindAuthenticationManagerFactory(contextSource);
//        factory.setUserSearchBase("ou=users");
//        factory.setUserSearchFilter("(uid={0})");
//        factory.setUserDetailsContextMapper(new InetOrgPersonContextMapper());
//        return factory.createAuthenticationManager();
//    }
//}
