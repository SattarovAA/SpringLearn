package com.rest.newsservice.web.model.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentResponse {
    private String content;
    private String userName;
    private Instant updatedTime;
}
