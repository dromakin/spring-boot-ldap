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
import com.dromakin.springbootldap.models.Service;


import java.util.List;

public interface ServiceGroupOfService {

    // find
    List<Service> getServicesByGroup(String group);
    boolean groupExists(String group);
    List<Group> getListOfGroups();
    Permission getGroupPermissionByName(String group);

    // create

    // update

    // delete

}
