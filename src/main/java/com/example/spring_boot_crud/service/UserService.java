package com.example.spring_boot_crud.service;

import com.example.spring_boot_crud.datamodel.User;
import com.example.spring_boot_crud.datamodel.UserRepository;
import com.example.spring_boot_crud.messages.CreateUserRequest;
import com.example.spring_boot_crud.messages.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository users;

    @Transactional
    public User create(String username, String password) {
        if (users.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists!");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        return users.save(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> authenticate(String username, String password) {
        Optional<User> userOpt = users.findByUsername(username);
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }
        User user = userOpt.get();
        if (!user.getPassword().equals(password)) {
            return Optional.empty();
        }
        return Optional.of(user);
    }

    public List<User> findAll() {
        return users.findAll();
    }
}
