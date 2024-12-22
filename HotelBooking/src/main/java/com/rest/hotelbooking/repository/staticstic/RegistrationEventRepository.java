package com.rest.hotelbooking.repository.staticstic;

import com.rest.hotelbooking.model.entity.statistic.RegistrationEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * MongoRepository for working with {@link RegistrationEvent} entity.
 */
public interface RegistrationEventRepository
        extends MongoRepository<RegistrationEvent, String> {
}
