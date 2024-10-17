package com.rest.hotelbooking.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record HotelFilter(
        @Min(value = 1, message = "Поле pageSize не должно быть меньше 1")
        @Max(value = 20, message = "Поле pageSize не должно быть больше 20")
        @NotNull(message = "Поле pageSize должно быть заполнено")
        Integer pageSize,
        @PositiveOrZero(message = "Поле pageNumber должно быть положительным")
        @NotNull(message = "Поле pageNumber должно быть заполнено")
        Integer pageNumber,
        Long id,
        @Size(max = 100)
        String name,
        @Size(max = 100)
        String adTitle,
        @Size(max = 100)
        String city,
        @Size(max = 100)
        String address,
        @PositiveOrZero(message = "Поле maxDistanceFromCenter должно быть положительным")
        Long maxDistanceFromCenter,
        @PositiveOrZero(message = "Поле minDistanceFromCenter должно быть положительным")
        Long minDistanceFromCenter,
        @Max(value = 5, message = "Поле maxRating должно быть не больше 5")
        BigDecimal maxRating,
        @PositiveOrZero(message = "Поле minRating должно быть положительным")
        BigDecimal minRating,
        @PositiveOrZero(message = "Поле maxNumberOfRatings должно быть положительным")
        Long maxNumberOfRatings,
        @PositiveOrZero(message = "Поле minNumberOfRatings должно быть положительным")
        Long minNumberOfRatings
) {
}