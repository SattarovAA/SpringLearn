package com.rest.newsservice.mapper;

import com.rest.newsservice.model.News;
import com.rest.newsservice.web.model.news.NewsListResponse;
import com.rest.newsservice.web.model.news.NewsRequest;
import com.rest.newsservice.web.model.news.NewsResponseWithComments;
import com.rest.newsservice.web.model.news.NewsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = CommentMapper.class)
public interface NewsMapper {
    @Mapping(source = "newsRequest.categoryName", target = "category.name")
    News requestToNews(NewsRequest newsRequest);

    @Mapping(source = "newsRequest.categoryName", target = "category.name")
    @Mapping(source = "newsId", target = "id")
    News requestToNews(Long newsId, NewsRequest newsRequest);

    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "user.username", target = "userName")
    @Mapping(source = "comments", target = "commentList")
    NewsResponseWithComments newsToResponseWithComments(News news);

    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "user.username", target = "userName")
    @Mapping(target = "commentAmount", expression = "java(news.getComments().size())")
    NewsResponse newsToResponseForList(News news);

    List<NewsResponse> newsListToListNewsResponse(List<News> news);

    default NewsListResponse newsListToNewsListResponse(List<News> news) {
        NewsListResponse response = new NewsListResponse();
        response.setNews(newsListToListNewsResponse(news));
        return response;
    }
}
