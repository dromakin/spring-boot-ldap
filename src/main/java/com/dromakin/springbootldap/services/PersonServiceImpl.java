/*
 * File:     PersonServiceImpl
 * Package:  com.dromakin.springbootldap.services
 * Project:  spring-boot-ldap
 *
 * Created by dromakin as 25.09.2023
 *
 * author - dromakin
 * maintainer - dromakin
 * version - 2023.09.25
 * copyright - ORGANIZATION_NAME Inc. 2023
 */
package com.dromakin.springbootldap.services;

import com.dromakin.springbootldap.mapper.PersonAttributesMapper;
import com.dromakin.springbootldap.models.Person;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    private static final String BASE_DN = "dc=example,dc=com";
    private static final Integer THREE_SECONDS = 30000;

    private LdapTemplate ldapTemplate;

    @Override
    public Person findByEmail(String email) {
        List<Person> personList = ldapTemplate.search(
                "ou=users",
                "mail=" + email,
                new PersonAttributesMapper()
        );

        return personList.isEmpty() ? new Person() : personList.get(0);
    }

    @Override
    public Person findByUid(String uid) {
        List<Person> personList = ldapTemplate.search(
                "ou=users",
                "uid=" + uid,
                new PersonAttributesMapper()
        );

        return personList.isEmpty() ? new Person() : personList.get(0);
    }

    @Override
    public List<Person> findByName(String name) {
        LdapQuery query = query()
                .where("objectclass").is("person")
                .and("cn").whitespaceWildcardsLike(name);

        return ldapTemplate.search(query, new PersonAttributesMapper());
    }

    @Override
    public List<Person> findByLastName(String lastName) {
        LdapQuery query = query()
                .where("objectclass").is("person")
                .and("sn").is(lastName);

        return ldapTemplate.search(query, new PersonAttributesMapper());
    }

    @Override
    public Person findByFullName(String cn) {
        List<Person> personList = ldapTemplate.search(
                "ou=users",
                "cn=" + cn,
                new PersonAttributesMapper()
        );

        return personList.isEmpty() ? new Person() : personList.get(0);
    }
}
