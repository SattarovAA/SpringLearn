package com.rest.hotelbooking.service;

import com.rest.hotelbooking.model.User;

public interface UserService extends CrudService<User>{
    User findByUsername(String username);
}
