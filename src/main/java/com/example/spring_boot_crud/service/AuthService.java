package com.example.spring_boot_crud.service;

import com.example.spring_boot_crud.datamodel.User;
import com.example.spring_boot_crud.messages.CreateUserRequest;
import com.example.spring_boot_crud.messages.LoginRequest;
import com.example.spring_boot_crud.messages.LoginResponse;
import com.example.spring_boot_crud.messages.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final TokenService tokenService;
    private final UserService userService;

    @Value("${app.jwt.access-min}")
    private Long accessMin;

    @Transactional(readOnly = true)
    public Optional<LoginResponse> login(LoginRequest request) {
        var userOpt = userService.findByUsername(request.username());
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }
        User user = userOpt.get();
        if (!user.getPassword().equals(request.password())) {
            return Optional.empty();
        }
        return Optional.of(new LoginResponse(tokenService.generateToken(user.getId(), user.getUsername()),
                accessMin * 60, UserMapper.toUserResponse(user)));
    }

    @Transactional
    public Optional<LoginResponse> registerAndLogin(CreateUserRequest request) {
        userService.create(request);
        return login(new LoginRequest(request.username(), request.password()));
    }
}
