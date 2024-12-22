package com.rest.hotelbooking.model.dto.user;

import com.rest.hotelbooking.model.security.RoleType;

import java.util.Set;

/**
 * Response DTO for working with entity user.
 *
 * @param id       user id.
 * @param username user username.
 * @param password user password.
 * @param email    user email.
 * @param roles    authentication {@link RoleType}.
 */
public record UserResponse(
        Long id,
        String username,
        String password,
        String email,
        Set<RoleType> roles
) {
}
