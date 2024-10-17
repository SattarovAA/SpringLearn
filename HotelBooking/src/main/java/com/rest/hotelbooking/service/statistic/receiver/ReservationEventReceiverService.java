package com.rest.hotelbooking.service.statistic.receiver;

import com.rest.hotelbooking.model.statistic.ReservationEvent;

import java.util.List;

public interface ReservationEventReceiverService {
    List<ReservationEvent> findAll();

    ReservationEvent save(ReservationEvent model);
}
