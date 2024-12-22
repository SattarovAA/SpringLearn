package com.rest.hotelbooking.service.security;

import com.rest.hotelbooking.model.entity.User;

/**
 * Service interface for work with new {@link User}.
 */
public interface SecurityService {
    /**
     * Registration new {@link User}.
     *
     * @param user {@link User} for registration.
     * @return registered {@link User}.
     */
    User registerNewUser(User user);
}
