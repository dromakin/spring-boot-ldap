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
package com.dromakin.springbootldap.services.ldap;

import com.dromakin.springbootldap.models.ldap.Person;

import java.util.List;

public interface PersonService {

    // find
    Person findByEmail(String email);
    Person findByUid(String uid);
    List<Person> findByName(String name);
    List<Person> findByLastName(String lastName);
    Person findByFullName(String cn);
    List<Person> findAll();

    // create

    // update

    // delete

}
