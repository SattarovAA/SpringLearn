package com.rest.hotelbooking.service.statistic.receiver;

import com.rest.hotelbooking.model.statistic.RegistrationEvent;

import java.util.List;

public interface RegistrationEventReceiverService {
    List<RegistrationEvent> findAll();

    RegistrationEvent save(RegistrationEvent model);
}
