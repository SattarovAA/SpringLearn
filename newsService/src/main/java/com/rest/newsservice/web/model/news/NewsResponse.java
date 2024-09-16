package com.rest.newsservice.web.model.news;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewsResponse {
    private String title;
    private String content;
    private String categoryName;
    private String userName;
    private Integer commentAmount;
}

