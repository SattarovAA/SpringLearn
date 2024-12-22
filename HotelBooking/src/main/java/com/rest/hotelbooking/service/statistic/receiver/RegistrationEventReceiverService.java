package com.rest.hotelbooking.service.statistic.receiver;

import com.rest.hotelbooking.model.entity.statistic.RegistrationEvent;

import java.util.List;

/**
 * Service interface for working with {@link RegistrationEvent}.
 */
public interface RegistrationEventReceiverService {
    /**
     * Find all {@link RegistrationEvent}.
     *
     * @return all {@link RegistrationEvent}.
     */
    List<RegistrationEvent> findAll();

    /**
     * Save object model of type {@link RegistrationEvent}.
     *
     * @param model {@link RegistrationEvent} to save.
     * @return {@link RegistrationEvent} that was saved.
     */
    RegistrationEvent save(RegistrationEvent model);
}
