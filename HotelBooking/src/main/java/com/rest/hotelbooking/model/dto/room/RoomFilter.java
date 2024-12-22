package com.rest.hotelbooking.model.dto.room;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * Filter for working with entity room.
 *
 * @param pageSize    page size.
 * @param pageNumber  page number.
 * @param id          id.
 * @param name        room name.
 * @param description room description.
 * @param number      room number.
 * @param maxCost     maximum cost.
 * @param minCost     minimum cost.
 * @param maxCapacity maximum capacity.
 * @param minCapacity minimum capacity.
 * @param hotelId     hotel id.
 * @param checkIn     day from which the booking starts.
 * @param checkOut    day from which the booking ends.
 */
public record RoomFilter(
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
        @Size(max = MAX_DESCRIPTION_SIZE)
        String description,
        @Size(max = MAX_NUMBER_SIZE)
        String number,
        @PositiveOrZero(message = "Field maxCost must be positive!")
        Long maxCost,
        @PositiveOrZero(message = "Field minCost must be positive!")
        Long minCost,
        @PositiveOrZero(message = "Field maxCapacity must be positive!")
        Long maxCapacity,
        @PositiveOrZero(message = "Field minCapacity must be positive!")
        Long minCapacity,
        Long hotelId,
        LocalDate checkIn,
        LocalDate checkOut
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
     * Maximum size of the number field.
     */
    private static final int MAX_NUMBER_SIZE = 100;
    /**
     * Maximum size of the description field.
     */
    private static final int MAX_DESCRIPTION_SIZE = 1000;
}
