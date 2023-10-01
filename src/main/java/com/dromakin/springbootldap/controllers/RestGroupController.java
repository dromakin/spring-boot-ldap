/*
 * File:     RestGroupController
 * Package:  com.dromakin.springbootldap.controllers
 * Project:  spring-boot-ldap
 *
 * Created by dromakin as 28.09.2023
 *
 * author - dromakin
 * maintainer - dromakin
 * version - 2023.09.28
 * copyright - ORGANIZATION_NAME Inc. 2023
 */
package com.dromakin.springbootldap.controllers;

import com.dromakin.springbootldap.config.SwaggerConfig;
import com.dromakin.springbootldap.dto.GroupDTO;
import com.dromakin.springbootldap.dto.ServiceDTO;
import com.dromakin.springbootldap.models.ldap.Group;
import com.dromakin.springbootldap.models.ldap.Permission;
import com.dromakin.springbootldap.models.ldap.Person;
import com.dromakin.springbootldap.services.ldap.GroupService;
import com.dromakin.springbootldap.services.ldap.PermissionService;
import com.dromakin.springbootldap.services.ldap.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api/group")
@AllArgsConstructor
public class RestGroupController {

    private final PersonService personService;
    private final PermissionService permissionService;
    private final GroupService groupService;

    @Operation(
            summary = "Get group by name",
            security = {@SecurityRequirement(name = SwaggerConfig.AUTH_SECURITY_SCHEME)},
            responses = {
                    @ApiResponse(description = "The group",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Group not found")
            }
    )
    @GetMapping("/name")
    public GroupDTO getGroupByName(
            Principal principal,
            @RequestParam
            @Parameter(description = "Group name", required = true)
            String name
    ) {
        final org.springframework.security.ldap.userdetails.Person personSecurity = (org.springframework.security.ldap.userdetails.Person) ((Authentication) principal).getPrincipal();
        Person person = personService.findByUid(personSecurity.getUsername());
        Group personGroup = groupService.getGroupByPerson(person);
        Permission permission = permissionService.getPermissionByGroup(personGroup);

        if (permission.equals(Permission.READ) || permission.equals(Permission.ALL)) {

            Group group = groupService.findBy("cn", name);
            Permission permissionGroup = permissionService.getPermissionByGroup(group);

            return GroupDTO.builder()
                    .name(name)
                    .members(group.getMembers().stream().map(String::valueOf).collect(Collectors.toList()))
                    .permissions(permissionGroup.toString())
                    .build();
        }

        return new GroupDTO();
    }

    @Operation(
            summary = "Get groups",
            security = {@SecurityRequirement(name = SwaggerConfig.AUTH_SECURITY_SCHEME)},
            responses = {
                    @ApiResponse(description = "Groups",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Groups not found")
            }
    )
    @GetMapping("/all")
    public List<String> getGroups(
            Principal principal
    ) {
        final org.springframework.security.ldap.userdetails.Person personSecurity = (org.springframework.security.ldap.userdetails.Person) ((Authentication) principal).getPrincipal();
        Person person = personService.findByUid(personSecurity.getUsername());
        Group personGroup = groupService.getGroupByPerson(person);
        Permission permission = permissionService.getPermissionByGroup(personGroup);

        if (permission.equals(Permission.READ) || permission.equals(Permission.ALL)) {

            List<Group> groups = groupService.getAllGroups();

            return groups.stream().map(Group::getName).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

}
