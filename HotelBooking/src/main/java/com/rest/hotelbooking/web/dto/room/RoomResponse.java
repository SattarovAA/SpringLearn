package com.rest.hotelbooking.web.dto.room;

import com.rest.hotelbooking.web.dto.reservation.ReservationResponse;

import java.util.List;

public record RoomResponse(
        Long id,
        String name,
        String description,
        String number,
        Long cost,
        Long capacity,
        Long hotelId,
        List<ReservationResponse> reservations
) {
}
