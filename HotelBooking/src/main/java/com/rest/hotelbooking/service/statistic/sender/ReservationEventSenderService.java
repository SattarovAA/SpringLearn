package com.rest.hotelbooking.service.statistic.sender;

import com.rest.hotelbooking.model.Reservation;

public interface ReservationEventSenderService {
    void send(Reservation model);
}
