package com.rest.hotelbooking.service.statistic.sender;

import com.rest.hotelbooking.model.entity.Reservation;

/**
 * Service interface for sending information
 * about creating a new {@link Reservation}.
 */
public interface ReservationEventSenderService {
    /**
     * Send information about creating a new {@link Reservation}.
     *
     * @param model {@link Reservation} for creation new event.
     */
    void send(Reservation model);
}
