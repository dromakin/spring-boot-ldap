/*
 * File:     DbInitializer
 * Package:  com.dromakin.springbootldap.config
 * Project:  spring-boot-ldap
 *
 * Created by dromakin as 01.10.2023
 *
 * author - dromakin
 * maintainer - dromakin
 * version - 2023.10.01
 * copyright - ORGANIZATION_NAME Inc. 2023
 */
package com.dromakin.springbootldap.config;

import com.dromakin.springbootldap.models.ldap.Person;
import com.dromakin.springbootldap.models.security.UserDb;
import com.dromakin.springbootldap.repositories.UserRepository;
import com.dromakin.springbootldap.services.ldap.GroupService;
import com.dromakin.springbootldap.services.ldap.PermissionService;
import com.dromakin.springbootldap.services.ldap.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class DbInitializer {

    private PersonService personService;
    private UserRepository userRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void loadDataFromLdap() {

        List<Person> personList = personService.findAll();

        for (int i = 0; i < personList.size(); i++) {
            Person person = personList.get(i);

            UserDb user = UserDb.builder()
                    .id((long) i)
                    .username(person.getUid())
                    .enabled(true)
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .build();

            userRepository.save(user);
        }
    }
}
