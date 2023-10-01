package com.dromakin.springbootldap.repositories;

import com.dromakin.springbootldap.models.security.UserDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDb, Long> {
    UserDb findByUsername(@Param("username") String username);

}
