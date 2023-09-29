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

import com.dromakin.springbootldap.models.Service;
import org.springframework.ldap.core.AttributesMapper;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

/**
 * Custom person attributes mapper, maps the attributes to the person POJO
 */
public class ServiceAttributesMapper implements AttributesMapper<Service> {
    @Override
    public Service mapFromAttributes(Attributes attributes) throws NamingException {
        return new Service(
                String.valueOf(attributes.get("cn").get()),
                String.valueOf(attributes.get("description").get())
        );
    }
}
