package com.rest.hotelbooking.model.dto.hotel;

import java.math.BigDecimal;

/**
 * Response DTO for working with entity hotel.
 * Not include rooms field.
 *
 * @param id                 hotel id.
 * @param name               hotel name.
 * @param adTitle            hotel adTitle.
 * @param city               hotel city.
 * @param address            hotel address.
 * @param distanceFromCenter hotel distance from center.
 * @param rating             hotel rating.
 * @param numberOfRatings    hotel number of ratings.
 */
public record HotelResponse(
        Long id,
        String name,
        String adTitle,
        String city,
        String address,
        Long distanceFromCenter,
        BigDecimal rating,
        Long numberOfRatings
) {
}
