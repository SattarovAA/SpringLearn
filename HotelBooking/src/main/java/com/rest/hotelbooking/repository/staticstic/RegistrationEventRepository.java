package com.rest.hotelbooking.repository.staticstic;

import com.rest.hotelbooking.model.statistic.RegistrationEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RegistrationEventRepository extends MongoRepository<RegistrationEvent, String> {
}
