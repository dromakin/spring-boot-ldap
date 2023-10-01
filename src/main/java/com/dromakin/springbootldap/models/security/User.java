/*
 * File:     User
 * Package:  com.dromakin.springbootldap.models.security
 * Project:  spring-boot-ldap
 *
 * Created by dromakin as 01.10.2023
 *
 * author - dromakin
 * maintainer - dromakin
 * version - 2023.10.01
 * copyright - ORGANIZATION_NAME Inc. 2023
 */
package com.dromakin.springbootldap.models.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class User extends UserDb implements UserDetails {

    private final String password;
    private final Collection<Authority> authorities;

    public User(Long id, String username, String password, boolean accountExpired, boolean accountLocked, boolean credentialsExpired, boolean enabled, Collection<Authority> authorities) {
        super(id, username, accountExpired, accountLocked, credentialsExpired, enabled);
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !isAccountExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isAccountLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isCredentialsExpired();
    }
}
