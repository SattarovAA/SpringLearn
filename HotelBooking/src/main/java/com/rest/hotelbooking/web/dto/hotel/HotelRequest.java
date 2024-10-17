package com.rest.hotelbooking.web.dto.hotel;

public record HotelRequest(
        String name,
        String adTitle,
        String city,
        String address,
        Long distanceFromCenter
) {
}
