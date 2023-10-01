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
package com.dromakin.springbootldap.services.ldap;

import com.dromakin.springbootldap.models.ldap.Service;

import java.util.List;

public interface ServiceOfService {

    boolean serviceNameExist(String name);
    Service getServiceByName(String name);
    List<Service> getServices();

}
