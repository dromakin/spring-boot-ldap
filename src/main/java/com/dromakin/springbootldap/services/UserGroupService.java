/*
 * File:     PersonService
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

import com.dromakin.springbootldap.models.Group;
import com.dromakin.springbootldap.models.Permission;
import com.dromakin.springbootldap.models.Person;

import java.util.List;

public interface UserGroupService {

    // find
    List<Person> getUsersByGroup(String group);
    boolean groupExists(String group);
    List<Group> getListOfGroups();
    Permission getGroupPermissionByNameGroup(String group);
    Permission getGroupPermissionByEmailUser(String email);

    // create

    // update

    // delete

}
