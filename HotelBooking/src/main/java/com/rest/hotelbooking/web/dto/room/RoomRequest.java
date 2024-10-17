package com.rest.hotelbooking.web.dto.room;

public record RoomRequest(
        String name,
        String description,
        String number,
        Long cost,
        Long capacity,
        Long hotelId) {
}