package com.example.spring_boot_crud.controller;

import com.example.spring_boot_crud.messages.CreateUserRequest;
import com.example.spring_boot_crud.messages.UserResponse;
import com.example.spring_boot_crud.service.UserMapper;
import com.example.spring_boot_crud.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.micrometer.common.util.StringUtils.isNotEmpty;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping
//    public ResponseEntity<?> register(@Valid @RequestBody CreateUserRequest user) {
//        String username = user.username();
//        String password = user.password();
//        if (isNotEmpty(username) && isNotEmpty(password)) {
//            userService.create(username, password);
//            return ResponseEntity.ok().build();
//        }
//        return ResponseEntity.badRequest().build();
//    }

//    @GetMapping
//    public List<UserResponse> findAll() {
//
//    }
}
