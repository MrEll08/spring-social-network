package com.example.spring_boot_crud.messages;

public record LoginResponse(String accessToken, long expiresInSeconds, UserResponse user) {}