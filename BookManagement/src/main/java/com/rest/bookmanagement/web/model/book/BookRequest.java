package com.rest.bookmanagement.web.model.book;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookRequest {
    @Length(min = 1,max = 20,message = "Поле title не должно быть длиной меньше 1,больше 20")
    @NotNull(message = "Поле title должно быть заполнено")
    private String title;
    @NotNull(message = "Поле author должно быть заполнено")
    private String author;
    @NotNull(message = "Поле content должно быть заполнено")
    private String content;
    @NotNull(message = "Поле categoryName должно быть заполнено")
    private String categoryName;
}
