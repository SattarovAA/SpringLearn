package com.rest.hotelbooking.model.dto.hotel;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * Filter for working with entity hotel.
 *
 * @param pageSize              page size.
 * @param pageNumber            page number.
 * @param id                    id.
 * @param name                  hotel name.
 * @param adTitle               hotel ad title.
 * @param city                  city location.
 * @param address               hotel address.
 * @param maxDistanceFromCenter maximum distance from center.
 * @param minDistanceFromCenter minimum distance from center.
 * @param maxRating             maximum rating.
 * @param minRating             minimum rating.
 * @param maxNumberOfRatings    maximum number of ratings.
 * @param minNumberOfRatings    minimum number of ratings.
 */
public record HotelFilter(
        @Min(value = MIN_PAGE_SIZE,
                message = "Field pageSize must not be less then "
                        + MIN_PAGE_SIZE)
        @Max(value = MAX_PAGE_SIZE,
                message = "Field pageSize must not be larger then "
                        + MAX_PAGE_SIZE)
        @NotNull(message = "Field pageSize must be filled!")
        Integer pageSize,
        @PositiveOrZero(message = "Field pageNumber must be positive!")
        @NotNull(message = "Field pageNumber must be filled!")
        Integer pageNumber,
        Long id,
        @Size(max = MAX_NAME_SIZE)
        String name,
        @Size(max = MAX_AD_TITLE_SIZE)
        String adTitle,
        @Size(max = MAX_CITY_SIZE)
        String city,
        @Size(max = MAX_ADDRESS_SIZE)
        String address,
        @PositiveOrZero(message = "maxDistanceFromCenter must be positive!")
        Long maxDistanceFromCenter,
        @PositiveOrZero(message = "minDistanceFromCenter must be positive!")
        Long minDistanceFromCenter,
        @Max(value = MAX_RATING,
                message = "Field maxRating must not be less then "
                        + MAX_RATING)
        BigDecimal maxRating,
        @PositiveOrZero(message = "Field minRating must be positive!")
        BigDecimal minRating,
        @PositiveOrZero(message = "Field maxNumberOfRatings must be positive!")
        Long maxNumberOfRatings,
        @PositiveOrZero(message = "Field minNumberOfRatings must be positive!")
        Long minNumberOfRatings
) {
    /**
     * Minimum size of the page field.
     */
    private static final int MIN_PAGE_SIZE = 1;
    /**
     * Maximum size of the page field.
     */
    private static final int MAX_PAGE_SIZE = 20;
    /**
     * Maximum size of the name field.
     */
    private static final int MAX_NAME_SIZE = 100;
    /**
     * Maximum size of the adTitle field.
     */
    private static final int MAX_AD_TITLE_SIZE = 100;
    /**
     * Maximum size of the city field.
     */
    private static final int MAX_CITY_SIZE = 100;
    /**
     * Maximum size of the address field.
     */
    private static final int MAX_ADDRESS_SIZE = 100;
    /**
     * Maximum size of the rating field.
     */
    private static final int MAX_RATING = 5;
}
