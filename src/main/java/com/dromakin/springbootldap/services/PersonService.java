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

import com.dromakin.springbootldap.models.Permission;
import com.dromakin.springbootldap.models.Person;

import java.util.List;

public interface PersonService {

    // find
    Person findByEmail(String email);
    List<Person> findByName(String name);
    List<Person> findByLastName(String lastName);
    Person findByFullName(String cn);
    Permission getPersonPermissionByEmailPerson(String email);

    // create

    // update

    // delete

}
