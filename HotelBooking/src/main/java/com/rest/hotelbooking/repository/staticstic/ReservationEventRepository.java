package com.rest.hotelbooking.repository.staticstic;

import com.rest.hotelbooking.model.statistic.ReservationEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReservationEventRepository extends MongoRepository<ReservationEvent, String> {
}
