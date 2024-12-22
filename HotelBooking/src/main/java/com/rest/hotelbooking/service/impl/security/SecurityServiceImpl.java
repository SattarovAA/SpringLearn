package com.rest.hotelbooking.service.impl.security;

import com.rest.hotelbooking.model.entity.User;
import com.rest.hotelbooking.service.UserService;
import com.rest.hotelbooking.service.security.SecurityService;
import com.rest.hotelbooking.service.statistic.sender.RegistrationEventSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service for work with new {@link User}.
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class SecurityServiceImpl implements SecurityService {
    /**
     * Service for work with {@link User} entity.
     */
    private final UserService userService;
    /**
     * Service for send user registration event.
     */
    private final RegistrationEventSenderService registrationEventSenderService;

    /**
     * Registration new {@link User}.
     * Send registered {@link User} information to
     * {@link RegistrationEventSenderService}.
     *
     * @param user {@link User} for registration.
     * @return registered {@link User}.
     */
    @Override
    public User registerNewUser(User user) {
        User registeredUser = userService.save(user);
        log.info("Creation new user with username {} and id {}",
                user.getUsername(),
                user.getId()
        );
        registrationEventSenderService.send(registeredUser);
        return registeredUser;
    }
}
