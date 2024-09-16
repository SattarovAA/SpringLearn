package com.rest.newsservice.web.model.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentRequest {
    @NotBlank(message = "Поле content должно быть заполнено")
    private String content;
    @NotNull(message = "Поле newsId должно быть заполнено")
    private Long newsId;
}
