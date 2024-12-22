package com.rest.hotelbooking.repository.staticstic;

import com.rest.hotelbooking.model.entity.statistic.ReservationEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * MongoRepository for working with {@link ReservationEvent} entity.
 */
public interface ReservationEventRepository
        extends MongoRepository<ReservationEvent, String> {
}
