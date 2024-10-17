package com.rest.hotelbooking.web.dto.hotel.mark;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public record HotelMark(
        @NotNull(message = "Поле value должно быть заполнено")
        @Range(min = 1, max = 5)
        Long value
) {
}
