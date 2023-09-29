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
package com.dromakin.springbootldap.services;

import com.dromakin.springbootldap.models.Group;
import com.dromakin.springbootldap.models.Permission;
import com.dromakin.springbootldap.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionService {

    // person groups
    private static final String PERSON_GROUP_USER = "user";
    private static final String PERSON_GROUP_EDITOR = "editor";
    private static final String PERSON_GROUP_ADMIN = "administrator";

    // service groups
    private static final String SERVICE_GROUP_READER = "reader";
    private static final String SERVICE_GROUP_WRITER = "writer";
    private static final String SERVICE_GROUP_ROOT = "root";

    private final GroupService groupService;

    @Autowired
    public PermissionServiceImpl(GroupService groupService) {
        this.groupService = groupService;
    }

    @Override
    public Permission getPermissionByPerson(Person person) {
        return getPermissionByGroup(groupService.getGroupByPerson(person));
    }

    @Override
    public Permission getPermissionByService(com.dromakin.springbootldap.models.Service service) {
        return getPermissionByGroup(groupService.getGroupByService(service));
    }

    @Override
    public Permission getPermissionByGroup(Group group) {
        if (group.isEmpty()) {
            return Permission.NONE;
        }

        switch (group.getName()) {
            case PERSON_GROUP_USER:
            case SERVICE_GROUP_READER:
                return Permission.READ;
            case PERSON_GROUP_EDITOR:
            case SERVICE_GROUP_WRITER:
                return Permission.WRITE;
            case PERSON_GROUP_ADMIN:
            case SERVICE_GROUP_ROOT:
                return Permission.ALL;
            default:
                return Permission.NONE;
        }
    }
}
