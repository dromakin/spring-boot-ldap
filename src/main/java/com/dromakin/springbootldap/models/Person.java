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

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.ldap.odm.annotations.*;
import org.springframework.ldap.support.LdapNameBuilder;

import javax.naming.Name;
import java.util.Objects;


@Entry(
        base = "ou=users",
        objectClasses = {"top", "person"}
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

    @DnAttribute(value = "ou")
    @Transient
    private String group; // users or services
    // group='users'

    public Person(String uid, String fullName, String lastName, String mail, String password) {
        this.uid = uid;
        this.dn = LdapNameBuilder.newInstance(BASE_DN)
                .add("ou", "users")
                .add("uid", uid)
                .build();
        this.fullName = fullName;
        this.lastName = lastName;
        this.mail = mail;
        this.password = password;
    }

    public Person(String fullName, String lastName, String mail, String password) {
        this(
                fullName.toLowerCase().replaceAll(lastName, "").replaceAll("\\s+", ""),
                fullName,
                lastName,
                mail,
                password
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(uid, person.uid) && Objects.equals(fullName, person.fullName) && Objects.equals(lastName, person.lastName) && Objects.equals(mail, person.mail) && Objects.equals(password, person.password) && Objects.equals(group, person.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, fullName, lastName, mail, password, group);
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
