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

import com.dromakin.springbootldap.mapper.GroupAttributesMapper;
import com.dromakin.springbootldap.models.Group;
import com.dromakin.springbootldap.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class GroupServiceImpl implements GroupService {

    private static final String BASE_DN = "dc=example,dc=com";

    private static final Integer THREE_SECONDS = 3000;
    private static final String OU_USERS = "users";
    private static final String OU_SERVICES = "services";

    // person groups
    private static final String PERSON_GROUP_USER = "user";
    private static final String PERSON_GROUP_EDITOR = "editor";
    private static final String PERSON_GROUP_ADMIN = "administrator";

    // service groups
    private static final String SERVICE_GROUP_READER = "reader";
    private static final String SERVICE_GROUP_WRITER = "writer";
    private static final String SERVICE_GROUP_ROOT = "root";

    private final LdapTemplate ldapTemplate;

    private final List<Group> personGroups;
    private final List<Group> serviceGroups;


    @Autowired
    public GroupServiceImpl(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
        this.personGroups = new ArrayList<>();
        this.serviceGroups = new ArrayList<>();
    }

    private void initGroups() {
        initPersonGroups();
        initServiceGroups();
    }

    private void initPersonGroups() {
        this.personGroups.add(this.findBy("cn", PERSON_GROUP_USER));
        this.personGroups.add(this.findBy("cn", PERSON_GROUP_EDITOR));
        this.personGroups.add(this.findBy("cn", PERSON_GROUP_ADMIN));
    }

    private void initServiceGroups() {
        this.serviceGroups.add(this.findBy("cn", SERVICE_GROUP_READER));
        this.serviceGroups.add(this.findBy("cn", SERVICE_GROUP_WRITER));
        this.serviceGroups.add(this.findBy("cn", SERVICE_GROUP_ROOT));
    }

    @Override
    public Group getGroupByPerson(Person person) {
        if (personGroups.isEmpty()) {
            initPersonGroups();
        }

        if (person.isEmpty()) {
            return new Group();
        }

        if (person.getGroup().equals(OU_USERS)) {
            for (Group group : personGroups) {
                Set<Name> members = group.getMembers();
                if (members.contains(person.getDn())) {
                    return group;
                }
            }
        }

        return new Group();
    }

    @Override
    public Group getGroupByService(com.dromakin.springbootldap.models.Service service) {
        if (serviceGroups.isEmpty()) {
            initServiceGroups();
        }

        if (service.isEmpty()) {
            return new Group();
        }

        if (service.getGroup().equals(OU_SERVICES)) {
            for (Group group : serviceGroups) {
                Set<Name> members = group.getMembers();
                if (members.contains(service.getDn())) {
                    return group;
                }
            }
        }

        return new Group();
    }

    @Override
    public List<Group> getAllGroups() {
        return ldapTemplate.findAll(Group.class);
    }

    @Override
    public Group findBy(String attr, String value) {

        List<Group> groupList = ldapTemplate.search(
                "ou=groups",
                attr + "=" + value,
                new GroupAttributesMapper()
        );

        return groupList.isEmpty() ? new Group() : groupList.get(0);
    }
}
