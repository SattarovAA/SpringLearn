package com.rest.newsservice.web.model.news;

import com.rest.newsservice.web.model.comment.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewsResponseWithComments {
    private String title;
    private String content;
    private String categoryName;
    private String userName;
    private List<CommentResponse> commentList;
}
