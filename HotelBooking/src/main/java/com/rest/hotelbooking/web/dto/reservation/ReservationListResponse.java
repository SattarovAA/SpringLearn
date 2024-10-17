package com.rest.hotelbooking.web.dto.reservation;

import java.util.List;

public record ReservationListResponse(
        List<ReservationResponse> reservations
) {
}
