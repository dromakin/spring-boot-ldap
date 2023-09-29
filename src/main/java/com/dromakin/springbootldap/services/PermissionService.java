/*
 * File:     PermissionService
 * Package:  com.dromakin.springbootldap.services
 * Project:  spring-boot-ldap
 *
 * Created by dromakin as 26.09.2023
 *
 * author - dromakin
 * maintainer - dromakin
 * version - 2023.09.26
 * copyright - ORGANIZATION_NAME Inc. 2023
 */
package com.dromakin.springbootldap.services;

import com.dromakin.springbootldap.models.Group;
import com.dromakin.springbootldap.models.Permission;
import com.dromakin.springbootldap.models.Person;
import com.dromakin.springbootldap.models.Service;

public interface PermissionService {

    // person
    Permission getPermissionByPerson(Person person);

    // service
    Permission getPermissionByService(Service service);

    // group
    Permission getPermissionByGroup(Group group);

}
