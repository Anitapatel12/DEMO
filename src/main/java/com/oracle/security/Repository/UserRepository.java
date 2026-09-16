package com.oracle.security.Repository;

import com.oracle.security.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository
        extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String
                                             username);
    boolean existsByUsername(String username);
}