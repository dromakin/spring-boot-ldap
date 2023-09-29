/*
 * File:     RestServiceController
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
import com.dromakin.springbootldap.dto.ServiceDTO;
import com.dromakin.springbootldap.models.Group;
import com.dromakin.springbootldap.models.Permission;
import com.dromakin.springbootldap.models.Person;
import com.dromakin.springbootldap.models.Service;
import com.dromakin.springbootldap.services.GroupService;
import com.dromakin.springbootldap.services.PermissionService;
import com.dromakin.springbootldap.services.PersonService;
import com.dromakin.springbootldap.services.ServiceOfService;
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

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api/service")
@AllArgsConstructor
public class RestServiceController {

    private final PersonService personService;
    private final ServiceOfService serviceOfService;
    private final PermissionService permissionService;
    private final GroupService groupService;

    @Operation(
            summary = "Get service",
            security = {@SecurityRequirement(name = SwaggerConfig.BASIC_AUTH_SECURITY_SCHEME)},
            responses = {
                    @ApiResponse(description = "The service",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Service not found")
            }
    )
    @GetMapping("/name")
    public ServiceDTO getServiceByName(
            Principal principal,
            @RequestParam
            @Parameter(description = "Service name", required = true)
            String name
    ) {
        final org.springframework.security.ldap.userdetails.Person personSecurity = (org.springframework.security.ldap.userdetails.Person) ((Authentication) principal).getPrincipal();
        Person person = personService.findByUid(personSecurity.getUsername());
        Group group = groupService.getGroupByPerson(person);
        Permission permission = permissionService.getPermissionByGroup(group);

        if (permission.equals(Permission.READ) || permission.equals(Permission.ALL)) {

            Service service = serviceOfService.getServiceByName(name);
            Group serviceGroup = groupService.getGroupByService(service);
            Permission servicePermission = permissionService.getPermissionByGroup(serviceGroup);

            if (service.isEmpty()) {
                new ServiceDTO();
            }

            return ServiceDTO.builder()
                    .name(name)
                    .description(service.getDescription())
                    .group(service.getGroup())
                    .permissions(servicePermission.toString())
                    .build();
        }

        return new ServiceDTO();
    }

    @Operation(
            summary = "Get services",
            security = {@SecurityRequirement(name = SwaggerConfig.BASIC_AUTH_SECURITY_SCHEME)},
            responses = {
                    @ApiResponse(description = "Services",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Services not found")
            }
    )
    @GetMapping("/all")
    public List<ServiceDTO> getServices(
            Principal principal
    ) {
        final org.springframework.security.ldap.userdetails.Person personSecurity = (org.springframework.security.ldap.userdetails.Person) ((Authentication) principal).getPrincipal();
        Person person = personService.findByUid(personSecurity.getUsername());
        Group group = groupService.getGroupByPerson(person);
        Permission permission = permissionService.getPermissionByGroup(group);

        List<ServiceDTO> services = new ArrayList<>();

        if (permission.equals(Permission.READ) || permission.equals(Permission.ALL)) {

            List<Service> serviceList = serviceOfService.getServices();

            for (Service service: serviceList) {
                Group serviceGroup = groupService.getGroupByService(service);
                Permission servicePermission = permissionService.getPermissionByGroup(serviceGroup);

                services.add(
                        ServiceDTO.builder()
                                .name(service.getName())
                                .description(service.getDescription())
                                .group(serviceGroup.getName())
                                .permissions(servicePermission.toString())
                                .build()
                );
            }

            return services;
        }

        return new ArrayList<>();
    }

}
