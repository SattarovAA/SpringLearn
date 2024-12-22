package com.rest.hotelbooking.service.statistic.sender;

import com.rest.hotelbooking.model.entity.User;

/**
 * Service interface for sending information about creating a new {@link User}.
 */
public interface RegistrationEventSenderService {
    /**
     * Send information about creating a new {@link User}.
     *
     * @param model {@link User} for creation new event.
     */
    void send(User model);
}
