package com.rest.hotelbooking.model.dto.hotel.mark;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

/**
 * Mark to update hotel rating.
 *
 * @param value mark value.
 */
public record HotelMark(
        @NotNull(message = "Field value must be filled!")
        @Range(min = MIN_RATING, max = MAX_RATING)
        Long value
) {
    /**
     * Maximum rating value.
     */
    private static final long MAX_RATING = 5;
    /**
     * Minimum rating value.
     */
    private static final long MIN_RATING = 1;
}
