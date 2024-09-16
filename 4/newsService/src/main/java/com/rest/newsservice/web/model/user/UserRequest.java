package com.rest.newsservice.web.model.user;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRequest {
    @NotBlank(message = "Поле name должно быть заполнено!")
    private String name;
}
