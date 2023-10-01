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
package com.dromakin.springbootldap.services.ldap;

import com.dromakin.springbootldap.models.ldap.Group;
import com.dromakin.springbootldap.models.ldap.Permission;
import com.dromakin.springbootldap.models.ldap.Person;
import com.dromakin.springbootldap.models.ldap.Service;

public interface PermissionService {

    // person
    Permission getPermissionByPerson(Person person);

    // service
    Permission getPermissionByService(Service service);

    // group
    Permission getPermissionByGroup(Group group);

}
