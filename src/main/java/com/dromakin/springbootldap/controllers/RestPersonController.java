package com.dromakin.springbootldap.controllers;

import com.dromakin.springbootldap.config.SwaggerConfig;
import com.dromakin.springbootldap.dto.PersonDTO;
import com.dromakin.springbootldap.models.ldap.Group;
import com.dromakin.springbootldap.models.ldap.Permission;
import com.dromakin.springbootldap.models.ldap.Person;
import com.dromakin.springbootldap.services.ldap.GroupService;
import com.dromakin.springbootldap.services.ldap.PermissionService;
import com.dromakin.springbootldap.services.ldap.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestPersonController {

    private final PersonService personService;
    private final PermissionService permissionService;
    private final GroupService groupService;

    @Autowired
    public RestPersonController(PersonService personService, PermissionService permissionService, GroupService groupService) {
        this.personService = personService;
        this.permissionService = permissionService;
        this.groupService = groupService;
    }

    @Operation(
            summary = "Get current Person",
            security = {@SecurityRequirement(name = SwaggerConfig.AUTH_SECURITY_SCHEME)},
            responses = {
                    @ApiResponse(description = "The current Person",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PersonDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Person not found")
            }
    )
    @GetMapping("/person")
    public PersonDTO getCurrentPerson(
            Principal principal
    ) {
        final org.springframework.security.ldap.userdetails.Person personSecurity = (org.springframework.security.ldap.userdetails.Person) ((Authentication) principal).getPrincipal();
        Person person = personService.findByUid(personSecurity.getUsername());
        Group group = groupService.getGroupByPerson(person);
        Permission permission = permissionService.getPermissionByGroup(group);

        return PersonDTO.builder()
                .fullName(person.getFullName())
                .mail(person.getMail())
                .role(person.getRole())
                .group(group.getName())
                .permissions(permission.name())
                .password(permission.equals(Permission.ALL) ? person.getPassword() : "ACCESS DENIED")
                .build();
    }

    @Operation(
            summary = "Get Person by email",
            security = {@SecurityRequirement(name = SwaggerConfig.AUTH_SECURITY_SCHEME)},
            responses = {
                    @ApiResponse(description = "The person",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PersonDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Person not found")
            }
    )
    @GetMapping("/person/email")
    public PersonDTO getPersonByEmail(
            Principal principal,
            @RequestParam
            @Parameter(description = "Person email", required = true)
            String email
    ) {
        final org.springframework.security.ldap.userdetails.Person personSecurity = (org.springframework.security.ldap.userdetails.Person) ((Authentication) principal).getPrincipal();
        Person currentPerson = personService.findByUid(personSecurity.getUsername());
        Group group = groupService.getGroupByPerson(currentPerson);
        Permission permission = permissionService.getPermissionByGroup(group);

        if (permission.equals(Permission.READ) || permission.equals(Permission.ALL)) {
            Person person = personService.findByEmail(email);
            Group personGroup = groupService.getGroupByPerson(person);
            Permission personPermission = permissionService.getPermissionByGroup(personGroup);

            if (person.isEmpty()) {
                return PersonDTO.builder().build();
            }

            return PersonDTO.builder()
                    .fullName(person.getFullName())
                    .mail(person.getMail())
                    .role(person.getRole())
                    .group(group.getName())
                    .permissions(personPermission.name())
                    .password(permission.equals(Permission.ALL) ? person.getPassword() : "ACCESS DENIED")
                    .build();
        }

        return PersonDTO.builder().build();
    }

    @Operation(
            summary = "Get Person by full name",
            security = {@SecurityRequirement(name = SwaggerConfig.AUTH_SECURITY_SCHEME)},
            responses = {
                    @ApiResponse(description = "The person",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PersonDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Person not found")
            }
    )
    @GetMapping("/person/fullName")
    public PersonDTO getPersonByFullName(
            Principal principal,
            @RequestParam
            @Parameter(description = "Person full name ('cn') ", required = true)
            String fullName
    ) {
        Person currentPerson = personService.findByUid(principal.getName());
        Group group = groupService.getGroupByPerson(currentPerson);
        Permission permission = permissionService.getPermissionByGroup(group);

        if (permission.equals(Permission.READ) || permission.equals(Permission.ALL)) {
            Person person = personService.findByFullName(fullName);
            Group personGroup = groupService.getGroupByPerson(person);
            Permission personPermission = permissionService.getPermissionByGroup(personGroup);

            return PersonDTO.builder()
                    .fullName(person.getFullName())
                    .mail(person.getMail())
                    .role(person.getRole())
                    .group(group.getName())
                    .permissions(personPermission.name())
                    .password(permission.equals(Permission.ALL) ? person.getPassword() : "ACCESS DENIED")
                    .build();
        }

        return PersonDTO.builder().build();
    }

    @Operation(
            summary = "Get persons by name",
            security = {@SecurityRequirement(name = SwaggerConfig.AUTH_SECURITY_SCHEME)},
            responses = {
                    @ApiResponse(description = "Persons by name",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ArraySchema.class))),
                    @ApiResponse(responseCode = "400", description = "Persons not found")
            }
    )
    @GetMapping("/person/name")
    public List<PersonDTO> getPersonByName(
            Principal principal,
            @RequestParam
            @Parameter(description = "Person like name in ('cn') ", required = true)
            String name
    ) {
        Person currentPerson = personService.findByUid(principal.getName());
        Group group = groupService.getGroupByPerson(currentPerson);
        Permission permission = permissionService.getPermissionByGroup(group);

        if (permission.equals(Permission.READ) || permission.equals(Permission.ALL)) {
            List<Person> persons = personService.findByName(name);
            List<PersonDTO> personDTOList = new ArrayList<>();

            for (Person person : persons) {
                Group personGroup = groupService.getGroupByPerson(person);
                Permission personPermission = permissionService.getPermissionByGroup(personGroup);
                PersonDTO personDTO = PersonDTO.builder()
                        .fullName(person.getFullName())
                        .mail(person.getMail())
                        .role(person.getRole())
                        .group(group.getName())
                        .permissions(personPermission.name())
                        .password(permission.equals(Permission.ALL) ? person.getPassword() : "ACCESS DENIED")
                        .build();
                personDTOList.add(personDTO);
            }

            return personDTOList;
        }

        return new ArrayList<>();
    }

    @Operation(
            summary = "Get persons by last name",
            security = {@SecurityRequirement(name = SwaggerConfig.AUTH_SECURITY_SCHEME)},
            responses = {
                    @ApiResponse(description = "Persons by last name",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ArraySchema.class))),
                    @ApiResponse(responseCode = "400", description = "Persons not found")
            }
    )
    @GetMapping("/person/lastName")
    public List<PersonDTO> getPersonByLastName(
            Principal principal,
            @RequestParam
            @Parameter(description = "The last name of Person ('sn') ", required = true)
            String lastName
    ) {
        Person currentPerson = personService.findByUid(principal.getName());
        Group group = groupService.getGroupByPerson(currentPerson);
        Permission permission = permissionService.getPermissionByGroup(group);

        if (permission.equals(Permission.READ) || permission.equals(Permission.ALL)) {
            List<Person> persons = personService.findByLastName(lastName);
            List<PersonDTO> personDTOList = new ArrayList<>();

            for (Person person : persons) {
                Group personGroup = groupService.getGroupByPerson(person);
                Permission personPermission = permissionService.getPermissionByGroup(personGroup);
                PersonDTO personDTO = PersonDTO.builder()
                        .fullName(person.getFullName())
                        .mail(person.getMail())
                        .role(person.getRole())
                        .group(group.getName())
                        .permissions(personPermission.name())
                        .password(permission.equals(Permission.ALL) ? person.getPassword() : "ACCESS DENIED")
                        .build();
                personDTOList.add(personDTO);
            }

            return personDTOList;
        }

        return new ArrayList<>();
    }

}
