package com.rest.hotelbooking.model.dto.statistic;

import lombok.Builder;

import java.time.LocalDate;

/**
 * DTO for working with entity ReservationEvent.
 *
 * @param userId   id of the user who created reservation.
 * @param checkIn  day from which the booking starts.
 * @param checkOut day from which the booking ends.
 */
@Builder
public record ReservationEventDto(
        Long userId,
        LocalDate checkIn,
        LocalDate checkOut
) {
}
