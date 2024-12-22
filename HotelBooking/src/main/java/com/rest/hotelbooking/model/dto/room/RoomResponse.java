package com.rest.hotelbooking.model.dto.room;

import com.rest.hotelbooking.model.dto.reservation.ReservationResponse;

import java.util.List;

/**
 * Response DTO for working with entity room.
 *
 * @param id           room id.
 * @param name         room name.
 * @param description  room description.
 * @param number       room number.
 * @param cost         room cost.
 * @param capacity     room capacity.
 * @param hotelId      hotel id where room is located.
 * @param reservations room reservations.
 */
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
