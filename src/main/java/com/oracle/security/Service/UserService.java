package com.oracle.security.Service;

import com.oracle.security.Repository.UserRepository;
import com.oracle.security.model.AppUser;
import com.oracle.security.model.RegisterRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public AppUser register(RegisterRequest request) {
        if (repo.existsByUsername(request.username())) {
            throw new IllegalArgumentException("Username already exists");
        }

        AppUser user = new AppUser();
        user.setUsername(request.username());
        user.setPassword(encoder.encode(request.password()));
        user.setRole("ROLE_USER");
        user.setEnabled(true);

        return repo.save(user);
    }
}