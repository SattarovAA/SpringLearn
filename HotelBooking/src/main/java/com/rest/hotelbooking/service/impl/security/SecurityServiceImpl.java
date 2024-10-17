package com.rest.hotelbooking.service.impl.security;

import com.rest.hotelbooking.model.User;
import com.rest.hotelbooking.service.UserService;
import com.rest.hotelbooking.service.security.SecurityService;
import com.rest.hotelbooking.service.statistic.sender.RegistrationEventSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SecurityServiceImpl implements SecurityService {
    private final UserService userService;
    private final RegistrationEventSenderService registrationEventSenderService;

    @Override
    public User registerNewUser(User user) {
        User registeredUser = userService.save(user);
        registrationEventSenderService.send(registeredUser);
        return registeredUser;
    }
}
