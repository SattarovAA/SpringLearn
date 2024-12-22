package com.rest.hotelbooking.model.dto.reservation;

import java.time.LocalDate;

/**
 * Request DTO for working with entity reservation.
 *
 * @param checkIn  day from which the booking starts.
 * @param checkOut day from which the booking ends.
 * @param roomId   the room id that was booked.
 */
public record ReservationRequest(
        LocalDate checkIn,
        LocalDate checkOut,
        Long roomId) {
}
