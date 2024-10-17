package com.rest.hotelbooking.web.dto.hotel;

import com.rest.hotelbooking.web.dto.room.RoomResponse;

import java.math.BigDecimal;
import java.util.List;

public record HotelResponseWith(
        Long id,
        String name,
        String adTitle,
        String city,
        String address,
        Long distanceFromCenter,
        BigDecimal rating,
        Long numberOfRatings,
        List<RoomResponse> rooms
) {
}
