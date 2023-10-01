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
package com.dromakin.springbootldap.models.ldap;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.ldap.odm.annotations.*;
import org.springframework.ldap.support.LdapNameBuilder;

import javax.naming.Name;

@Entry(
        base = "ou=services",
        objectClasses = {"top", "organizationalRole"}
)
@NoArgsConstructor
@Data
@ToString
public class Service {

    private static final String BASE_DN = "dc=example,dc=com";

    @Id
    private Name dn;

    @Attribute(name = "cn")
    private String name;

    @Attribute(name = "description")
    private String description;

    @DnAttribute(value = "ou")
    @Transient
    private String group;

    public Service(String name, String description) {
        this.dn = LdapNameBuilder.newInstance(BASE_DN)
                .add("ou", "services")
                .add("cn", name.toLowerCase())
                .build();
        this.name = name;
        this.description = description;
        this.group = "services";
    }

    public boolean isEmpty() {
        return name == null || description == null || dn == null || group == null;
    }


    /*
dn: cn=broker,ou=services,dc=example,dc=com
objectClass: top
objectClass: organizationalRole
cn: Broker
description: Service for message processing
     */
}
