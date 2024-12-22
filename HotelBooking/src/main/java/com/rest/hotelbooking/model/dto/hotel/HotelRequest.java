package com.rest.hotelbooking.model.dto.hotel;

/**
 * Request DTO for working with entity hotel.
 *
 * @param name               hotel name.
 * @param adTitle            hotel adTitle.
 * @param city               hotel city.
 * @param address            hotel address.
 * @param distanceFromCenter hotel distance from center.
 */
public record HotelRequest(
        String name,
        String adTitle,
        String city,
        String address,
        Long distanceFromCenter
) {
}
