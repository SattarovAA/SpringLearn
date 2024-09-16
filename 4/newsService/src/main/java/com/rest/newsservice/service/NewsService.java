package com.rest.newsservice.service;

import com.rest.newsservice.model.News;
import com.rest.newsservice.web.model.NewsFilter;

import java.util.List;

public interface NewsService extends CrudService<News> {
    List<News> filterBy(NewsFilter filter);

    void enrich(News requestNews);
}
