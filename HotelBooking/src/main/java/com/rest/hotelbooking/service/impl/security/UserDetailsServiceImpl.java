package com.rest.hotelbooking.service.impl.security;

import com.rest.hotelbooking.model.User;
import com.rest.hotelbooking.model.security.AppUserDetails;
import com.rest.hotelbooking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userService.findByUsername(username);
        return new AppUserDetails(user);
    }
}
