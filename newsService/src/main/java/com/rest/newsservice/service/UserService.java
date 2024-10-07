package com.rest.newsservice.service;

import com.rest.newsservice.model.User;

public interface UserService extends CrudService<User> {
    User findByUsername(String username);
}
