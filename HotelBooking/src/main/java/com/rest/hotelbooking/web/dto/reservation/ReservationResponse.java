package com.rest.hotelbooking.web.dto.reservation;

import java.time.LocalDate;

public record ReservationResponse(
        Long id,
        LocalDate checkIn,
        LocalDate checkOut,
        Long roomId) {
}
