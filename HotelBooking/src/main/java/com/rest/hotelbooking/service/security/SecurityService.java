package com.rest.hotelbooking.service.security;

import com.rest.hotelbooking.model.User;

public interface SecurityService {
    User registerNewUser(User user);
}
