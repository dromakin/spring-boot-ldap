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
import com.dromakin.springbootldap.models.Person;
import com.dromakin.springbootldap.models.Service;

import java.util.List;

public interface GroupService {

    // find
    Group getGroupByPerson(Person person);

    Group getGroupByService(Service service);

    List<Group> getAllGroups();

    Group findBy(String attr, String value);

    // create

    // update

    // delete

}
