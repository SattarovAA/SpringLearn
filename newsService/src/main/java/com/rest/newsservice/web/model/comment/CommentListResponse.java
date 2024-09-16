package com.rest.newsservice.web.model.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentListResponse {
    List<CommentResponse> comments = new ArrayList<>();
}
