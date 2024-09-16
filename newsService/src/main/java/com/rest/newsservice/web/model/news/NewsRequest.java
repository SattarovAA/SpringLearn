package com.rest.newsservice.web.model.news;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewsRequest {
    @NotBlank(message = "Поле title должно быть заполнено!")
    @Size(max = 32, message = "Поле title должно быть не больше 32 символов")
    private String title;
    @NotBlank(message = "Поле content должно быть заполнено!")
    private String content;
    @NotBlank(message = "Поле categoryName должно быть заполнено!")
    private String categoryName;
}
