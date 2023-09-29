/*
 * File:     ServiceOfService
 * Package:  com.dromakin.springbootldap.services
 * Project:  spring-boot-ldap
 *
 * Created by dromakin as 24.09.2023
 *
 * author - dromakin
 * maintainer - dromakin
 * version - 2023.09.24
 * copyright - ORGANIZATION_NAME Inc. 2023
 */
package com.dromakin.springbootldap.services;

import com.dromakin.springbootldap.mapper.ServiceAttributesMapper;
import com.dromakin.springbootldap.models.Service;
import lombok.AllArgsConstructor;
import org.springframework.ldap.core.LdapTemplate;

import java.util.List;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ServiceOfServiceImpl implements ServiceOfService {

    private final LdapTemplate ldapTemplate;

    private List<Service> findServiceByAttr(String name) {
        return ldapTemplate.search(
                "ou=services",
                "cn=" + name,
                new ServiceAttributesMapper()
        );
    }

    @Override
    public boolean serviceNameExist(String name) {
        return !findServiceByAttr(name).isEmpty();
    }

    @Override
    public Service getServiceByName(String name) {
        List<Service> services = findServiceByAttr(name);
        return services.isEmpty() ? new Service() : services.get(0);
    }

    @Override
    public List<Service> getServices() {
        return ldapTemplate.search(
                "ou=services",
                "objectClass=organizationalRole",
                new ServiceAttributesMapper()
        );
    }

}
