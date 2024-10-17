package com.rest.hotelbooking.web.dto.reservation;

import java.time.LocalDate;

public record ReservationRequest(
        LocalDate checkIn,
        LocalDate checkOut,
        Long roomId) {
}
