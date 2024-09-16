package com.rest.newsservice.service;

import com.rest.newsservice.model.User;

import java.util.UUID;

public interface UserService extends CrudService<User> {
    User findByUuid(UUID uuid);
}
