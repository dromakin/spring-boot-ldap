package com.dromakin.springbootldap.models.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;


@Data
@EqualsAndHashCode
@AllArgsConstructor
public class Authority implements GrantedAuthority {

    private String name;

    @Override
    public String getAuthority() {
        return getName();
    }
}
