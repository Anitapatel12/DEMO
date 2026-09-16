package com.oracle.security;

import com.oracle.security.Repository.UserRepository;
import com.oracle.security.model.AppUser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DemoDataConfig {

    @Bean
    CommandLineRunner seed(UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (!repo.existsByUsername("user")) {
                repo.save(new AppUser(
                        null,
                        "user",
                        encoder.encode("user123"),
                        "ROLE_USER",
                        true
                ));
            }

            if (!repo.existsByUsername("admin")) {
                repo.save(new AppUser(
                        null,
                        "admin",
                        encoder.encode("admin123"),
                        "ROLE_ADMIN",
                        true
                ));
            }
        };
    }
}