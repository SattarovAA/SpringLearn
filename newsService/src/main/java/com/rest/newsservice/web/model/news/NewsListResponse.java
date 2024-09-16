package com.rest.newsservice.web.model.news;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NewsListResponse {
    List<NewsResponse> news = new ArrayList<>();
}
