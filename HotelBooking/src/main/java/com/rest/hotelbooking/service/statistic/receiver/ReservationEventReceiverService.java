package com.rest.hotelbooking.service.statistic.receiver;

import com.rest.hotelbooking.model.entity.statistic.ReservationEvent;

import java.util.List;

/**
 * Service interface for working with {@link ReservationEvent}.
 */
public interface ReservationEventReceiverService {
    /**
     * Find all {@link ReservationEvent}.
     *
     * @return all {@link ReservationEvent}.
     */
    List<ReservationEvent> findAll();

    /**
     * Save object model of type {@link ReservationEvent}.
     *
     * @param model {@link ReservationEvent} to save.
     * @return {@link ReservationEvent} that was saved.
     */
    ReservationEvent save(ReservationEvent model);
}
