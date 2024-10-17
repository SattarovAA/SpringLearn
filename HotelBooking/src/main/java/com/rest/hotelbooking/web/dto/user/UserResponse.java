package com.rest.hotelbooking.web.dto.user;

import com.rest.hotelbooking.model.security.RoleType;

import java.util.Set;

public record UserResponse(
        Long id,
        String username,
        String password,
        String email,
        Set<RoleType> roles
) {
}
