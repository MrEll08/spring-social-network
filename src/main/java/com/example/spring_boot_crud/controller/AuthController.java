package com.example.spring_boot_crud.controller;

import com.example.spring_boot_crud.messages.CreateUserRequest;
import com.example.spring_boot_crud.messages.LoginRequest;
import com.example.spring_boot_crud.messages.LoginResponse;
import com.example.spring_boot_crud.security.CookieSupport;
import com.example.spring_boot_crud.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final CookieSupport cookies;

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@Valid @RequestBody CreateUserRequest request) {
        return authService.registerAndLogin(request)
                .map(resp -> ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE,
                                cookies.buildAccessCookie(resp.accessToken(), resp.expiresInSeconds()).toString())
                        .body(resp))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request)
            .map(resp -> ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,
                    cookies.buildAccessCookie(resp.accessToken(), resp.expiresInSeconds()).toString())
                .body(resp))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, cookies.clearAccessCookie().toString())
                .build();
    }
}
