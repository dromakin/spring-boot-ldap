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
public class UserGroupServiceImpl implements UserGroupService {

    private static final Integer THREE_SECONDS = 3000;

    private LdapTemplate ldapTemplate;

    @Override
    public List<Person> getUsersByGroup(String group) {

        LdapQuery query = query()
                .searchScope(SearchScope.SUBTREE)
                .timeLimit(THREE_SECONDS)
                .attributes("cn", "sn", "uid", "mail", "userpassword")
                .base(LdapUtils.emptyLdapName())
                .where("objectclass").is("person")
                .and("uid").isPresent()
                .and("cn").whitespaceWildcardsLike(name)
                .or("cn").like(name);

        return null;
    }

    @Override
    public boolean groupExists(String group) {
        return false;
    }

    @Override
    public List<Group> getListOfGroups() {
        return null;
    }

    @Override
    public Permission getGroupPermissionByNameGroup(String group) {
        return null;
    }

    @Override
    public Permission getGroupPermissionByEmailUser(String email) {
        return null;
    }
}
