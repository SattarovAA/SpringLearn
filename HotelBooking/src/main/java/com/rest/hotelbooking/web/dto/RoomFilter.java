package com.rest.hotelbooking.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record RoomFilter(
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
        @Size(max = 1000)
        String description,
        @Size(max = 100)
        String number,
        @PositiveOrZero(message = "Поле maxCost должно быть положительным")
        Long maxCost,
        @PositiveOrZero(message = "Поле minCost должно быть положительным")
        Long minCost,
        @PositiveOrZero(message = "Поле maxCapacity должно быть положительным")
        Long maxCapacity,
        @PositiveOrZero(message = "Поле minCapacity должно быть положительным")
        Long minCapacity,
        Long hotelId,
        LocalDate checkIn,
        LocalDate checkOut
) {
}
