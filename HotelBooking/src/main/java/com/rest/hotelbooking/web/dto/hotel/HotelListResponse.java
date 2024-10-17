package com.rest.hotelbooking.web.dto.hotel;

import java.util.List;

public record HotelListResponse(
        List<HotelResponse> hotels
) {
}
