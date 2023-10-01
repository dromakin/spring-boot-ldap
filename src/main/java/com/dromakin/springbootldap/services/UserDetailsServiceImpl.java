package com.dromakin.springbootldap.services;

import com.dromakin.springbootldap.models.ldap.Group;
import com.dromakin.springbootldap.models.ldap.Permission;
import com.dromakin.springbootldap.models.ldap.Person;
import com.dromakin.springbootldap.models.security.Authority;
import com.dromakin.springbootldap.models.security.User;
import com.dromakin.springbootldap.models.security.UserDb;
import com.dromakin.springbootldap.repositories.UserRepository;
import com.dromakin.springbootldap.services.ldap.GroupService;
import com.dromakin.springbootldap.services.ldap.PermissionService;
import com.dromakin.springbootldap.services.ldap.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private PersonService personService;
    private GroupService groupService;
    private PermissionService permissionService;
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // LDAP
        Person person = personService.findByUid(username);
        Group group = groupService.getGroupByPerson(person);
        Permission permission = permissionService.getPermissionByGroup(group);

        // Postgres
        UserDb userDb = userRepository.findByUsername(username);

        // Authority
        Authority authority = new Authority(permission.name());

        if (userDb != null) {
            return new User(
                    userDb.getId(),
                    userDb.getUsername(),
                    person.getPassword(),
                    userDb.isAccountExpired(),
                    userDb.isAccountLocked(),
                    userDb.isCredentialsExpired(),
                    userDb.isEnabled(),
                    Collections.singletonList(authority)
            );
        }

        throw new UsernameNotFoundException(username);
    }
}
