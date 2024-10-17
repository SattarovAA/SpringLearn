package com.rest.hotelbooking.web.dto.statistic;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ReservationEventDto(
        Long userId,
        LocalDate checkIn,
        LocalDate checkOut
) {
}
