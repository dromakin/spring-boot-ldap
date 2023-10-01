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
        base = "ou=users",
        objectClasses = {"top", "person", "organizationalPerson", "inetOrgPerson"}
)
@NoArgsConstructor
@Data
@ToString
public class Person {

    private static final String BASE_DN = "dc=example,dc=com";

    @Id
    private Name dn;

    @DnAttribute(value = "uid")
    private String uid;

    @Attribute(name = "cn")
    private String fullName;

    @Attribute(name = "sn")
    private String lastName;

    @Attribute(name = "mail")
    private String mail;

    @Attribute(name = "userpassword")
    private String password;

    @Attribute(name = "description")
    private String role;

    @DnAttribute(value = "ou")
    @Transient
    private String group; // users or services
    // group='users'

    public Person(String uid, String fullName, String lastName, String mail, String password, String role) {
        this.uid = uid;
        this.dn = LdapNameBuilder.newInstance(BASE_DN)
                .add("ou", "users")
                .add("uid", uid)
                .build();
        this.fullName = fullName;
        this.lastName = lastName;
        this.mail = mail;
        this.password = password;
        this.group = "users";
        this.role = role;
    }

    public boolean isEmpty() {
        return dn == null || uid == null || fullName == null || lastName == null || mail == null || password == null || group == null || role == null;
    }

    /*
dn: uid=alice,ou=users,dc=example,dc=com
objectClass: top
objectClass: person
objectClass: organizationalPerson
objectClass: inetOrgPerson
cn: Alice Johnson
sn: Johnson
uid: alice
mail: alice@example.com
userpassword: {MD5}...
     */

}
