package com.rest.hotelbooking.model.dto.hotel;

import com.rest.hotelbooking.model.dto.room.RoomResponse;

import java.math.BigDecimal;
import java.util.List;

/**
 * Response DTO for working with entity hotel.
 * Include rooms field.
 *
 * @param id                 hotel id.
 * @param name               hotel name.
 * @param adTitle            hotel adTitle.
 * @param city               hotel city.
 * @param address            hotel address.
 * @param distanceFromCenter hotel distance from center.
 * @param rating             hotel rating.
 * @param numberOfRatings    hotel number of ratings.
 * @param rooms              hotel rooms.
 */
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
