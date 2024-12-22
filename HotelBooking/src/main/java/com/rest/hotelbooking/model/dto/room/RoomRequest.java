package com.rest.hotelbooking.model.dto.room;

/**
 * Request DTO for working with entity room.
 *
 * @param name        room name.
 * @param description room description.
 * @param number      room number.
 * @param cost        room cost.
 * @param capacity    room capacity.
 * @param hotelId     hotel where room is located.
 */
public record RoomRequest(
        String name,
        String description,
        String number,
        Long cost,
        Long capacity,
        Long hotelId) {
}
