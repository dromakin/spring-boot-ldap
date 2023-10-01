/*
 * File:     Group
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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;
import org.springframework.ldap.support.LdapNameBuilder;

import javax.naming.Name;
import java.util.Set;

@Entry(
        base = "ou=groups",
        objectClasses = {"top", "groupOfNames"}
)
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Group {

    private static final String BASE_DN = "dc=example,dc=com";

    @Id
    private Name dn;

    @Attribute(name = "cn")
    @DnAttribute("cn")
    private String name;

    @Attribute(name = "member")
    private Set<Name> members;
    // members=[uid=jihn,ou=people,dc=example,dc=com]

    public Group(String name, Set<Name> members) {
        this.dn = LdapNameBuilder.newInstance(BASE_DN)
                .add("ou", "groups")
                .add("cn", name)
                .build();
        this.name = name;
        this.members = members;
    }

    public boolean isEmpty() {
        return dn == null || name == null || members == null;
    }
    /*
dn: cn=user,ou=groups,dc=example,dc=com
objectClass: top
objectClass: groupOfNames
cn: user
member: uid=alice,ou=users,dc=example,dc=com
member: uid=bob,ou=users,dc=example,dc=com
member: uid=diana,ou=users,dc=example,dc=com

dn: cn=reader,ou=groups,dc=example,dc=com
objectClass: top
objectClass: groupOfNames
cn: reader
member: cn=dashboard,ou=services,dc=example,dc=com
member: cn=prometheus,ou=services,dc=example,dc=com
     */
}
