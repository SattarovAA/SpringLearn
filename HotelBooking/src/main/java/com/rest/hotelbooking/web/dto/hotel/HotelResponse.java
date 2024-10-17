package com.rest.hotelbooking.web.dto.hotel;

import java.math.BigDecimal;
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
