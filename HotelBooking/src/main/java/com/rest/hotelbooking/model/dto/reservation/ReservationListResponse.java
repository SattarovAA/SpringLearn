package com.rest.hotelbooking.model.dto.reservation;

import java.util.List;

/**
 * List Response DTO for working with entity reservation.
 *
 * @param reservations list of {@link ReservationResponse}.
 */
public record ReservationListResponse(
        List<ReservationResponse> reservations
) {
}
