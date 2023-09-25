/*
 * File:     Person
 * Package:  com.dromakin.springbootldap.models
 * Project:  spring-boot-ldap
 *
 * Created by dromakin as 24.09.2023
 *
 * author - dromakin
 * maintainer - dromakin
 * version - 2023.09.24
 * copyright - ORGANIZATION_NAME Inc. 2023
 */
package com.dromakin.springbootldap.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.ldap.odm.annotations.Entry;

//@AllArgsConstructor
//@Data
//@Builder
@Entry(
        base = "ou=users",
        objectClasses = { "person", "inetOrgPerson", "top" }
)
public class Person {
    private String name;
    private String uid;
    private String email;
    private String password;
}