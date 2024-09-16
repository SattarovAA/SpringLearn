package com.rest.newsservice.web.model.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryRequest {
    @NotBlank(message = "Поле name должно быть заполнено!")
    @Size(max = 32, message = "Поле name должно быть не больше 32 символов")
    private String name;
}
