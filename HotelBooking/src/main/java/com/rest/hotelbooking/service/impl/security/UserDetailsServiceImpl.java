package com.rest.hotelbooking.service.impl.security;

import com.rest.hotelbooking.model.entity.User;
import com.rest.hotelbooking.model.security.AppUserDetails;
import com.rest.hotelbooking.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Service for working with {@link AppUserDetails}.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    /**
     * Service for working with entity {@link User}.
     */
    private final UserService userService;

    /**
     * Load {@link User} by username from {@link UserService}.
     *
     * @param username username searched {@link User}.
     * @return {@link AppUserDetails} with th found user.
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userService.findByUsername(username);
        log.info("load user with {} username.", user.getUsername());
        return new AppUserDetails(user);
    }
}
