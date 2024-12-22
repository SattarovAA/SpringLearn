package com.rest.hotelbooking.service;

import com.rest.hotelbooking.model.entity.User;

/**
 * Default interface service for working with entity {@link User}.
 */
public interface UserService extends CrudService<User> {
    /**
     * Find {@link User} with User.username equals username.
     *
     * @param username username searched {@link User}.
     * @return {@link User} with searched username.
     */
    User findByUsername(String username);
}
