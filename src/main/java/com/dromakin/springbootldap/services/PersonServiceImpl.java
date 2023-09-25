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

import com.dromakin.springbootldap.models.Permission;
import com.dromakin.springbootldap.models.Person;
import com.dromakin.springbootldap.pojo.PersonAttributesMapper;
import lombok.AllArgsConstructor;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.SearchScope;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {

    private static final Integer THREE_SECONDS = 3000;

    private LdapTemplate ldapTemplate;

    @Override
    public Person findByEmail(String email) {

        LdapQuery query = query()
                .searchScope(SearchScope.SUBTREE)
                .timeLimit(THREE_SECONDS)
                .countLimit(1)
                .attributes("cn", "sn", "uid", "mail", "userpassword")
                .base(LdapUtils.emptyLdapName())
                .where("objectclass").is("person")
                .and("mail").is(email)
                .and("uid").isPresent();

        List<Person> personList = ldapTemplate.search(query, new PersonAttributesMapper());

        return personList.isEmpty() ? Person.builder().build() : personList.get(0);
    }

    @Override
    public List<Person> findByName(String name) {

        LdapQuery query = query()
                .searchScope(SearchScope.SUBTREE)
                .timeLimit(THREE_SECONDS)
                .attributes("cn", "sn", "uid", "mail", "userpassword")
                .base(LdapUtils.emptyLdapName())
                .where("objectclass").is("person")
                .and("uid").isPresent()
                .and("cn").whitespaceWildcardsLike(name)
                .or("cn").like(name);

        return ldapTemplate.search(query, new PersonAttributesMapper());
    }

    @Override
    public List<Person> findByLastName(String lastName) {

        LdapQuery query = query()
                .searchScope(SearchScope.SUBTREE)
                .timeLimit(THREE_SECONDS)
                .attributes("cn", "sn", "uid", "mail", "userpassword")
                .base(LdapUtils.emptyLdapName())
                .where("objectclass").is("person")
                .and("uid").isPresent()
                .and("sn").is(lastName)
                .or("sn").like(lastName);

        return ldapTemplate.search(query, new PersonAttributesMapper());
    }

    @Override
    public Person findByFullName(String cn) {
        LdapQuery query = query()
                .searchScope(SearchScope.SUBTREE)
                .timeLimit(THREE_SECONDS)
                .countLimit(1)
                .attributes("cn", "sn", "uid", "mail", "userpassword")
                .base(LdapUtils.emptyLdapName())
                .where("objectclass").is("person")
                .and("uid").isPresent()
                .and("cn").is(cn);

        List<Person> personList = ldapTemplate.search(query, new PersonAttributesMapper());

        return personList.isEmpty() ? Person.builder().build() : personList.get(0);
    }

    @Override
    public Permission getPersonPermissionByEmailPerson(String email) {
        return null;
    }
}
