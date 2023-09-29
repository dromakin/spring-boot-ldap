/*
 * File:     PersonAttributesMapper
 * Package:  com.dromakin.springbootldap.pojo
 * Project:  spring-boot-ldap
 *
 * Created by dromakin as 25.09.2023
 *
 * author - dromakin
 * maintainer - dromakin
 * version - 2023.09.25
 * copyright - ORGANIZATION_NAME Inc. 2023
 */
package com.dromakin.springbootldap.mapper;

import com.dromakin.springbootldap.models.Group;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapAttribute;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.ldap.LdapName;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Custom person attributes mapper, maps the attributes to the person POJO
 */
public class GroupAttributesMapper implements AttributesMapper<Group> {
    @Override
    public Group mapFromAttributes(Attributes attributes) throws NamingException {
        Set<Name> members = new HashSet<>();

        for (int i = 0; i < attributes.get("member").size(); i++) {
            members.add(new LdapName((String) attributes.get("member").get(i)));
        }

        return new Group(
                String.valueOf(attributes.get("cn").get()),
                members
        );
    }
}
