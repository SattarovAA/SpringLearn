package com.rest.newsservice.web.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class NewsFilter {
    @Min(value = 1, message = "Поле pageSize не должно быть меньше 1")
    @Max(value = 20, message = "Поле pageSize не должно быть больше 20")
    @NotNull(message = "Поле pageSize должно быть заполнено")
    private Integer pageSize;
    @PositiveOrZero(message = "Поле pageNumber должно быть положительным")
    @NotNull(message = "Поле pageNumber должно быть заполнено")
    private Integer pageNumber;
    private String categoryName;
    private String userName;
}
