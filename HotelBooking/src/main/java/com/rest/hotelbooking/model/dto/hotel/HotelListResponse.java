package com.rest.hotelbooking.model.dto.hotel;

import java.util.List;

/**
 * List Response DTO for working with entity hotel.
 *
 * @param hotels list of {@link HotelResponse}.
 */
public record HotelListResponse(
        List<HotelResponse> hotels
) {
}
