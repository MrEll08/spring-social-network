package com.example.spring_boot_crud.service;

import com.example.spring_boot_crud.datamodel.User;
import com.example.spring_boot_crud.messages.UserResponse;

public final class UserMapper {
    private UserMapper() {}

    public static UserResponse toUserResponse(User user) {
        return new UserResponse(user.getId(), user.getUsername());
    }
}
