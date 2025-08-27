package com.example.spring_boot_crud.messages;

import java.util.UUID;

public record UserResponse(UUID id, String username) {
}
