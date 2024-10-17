package com.rest.hotelbooking.service.statistic.sender;

import com.rest.hotelbooking.model.User;

public interface RegistrationEventSenderService {
    void send(User model);
}
