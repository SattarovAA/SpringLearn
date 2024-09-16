package com.rest.newsservice.web.controller;

import com.rest.newsservice.mapper.NewsMapper;
import com.rest.newsservice.service.NewsService;
import com.rest.newsservice.web.model.NewsFilter;
import com.rest.newsservice.web.model.news.NewsListResponse;
import com.rest.newsservice.web.model.news.NewsRequest;
import com.rest.newsservice.web.model.news.NewsResponseWithComments;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/news")
public class NewsController {
    private final NewsService newsService;
    private final NewsMapper newsMapper;

    @GetMapping()
    public ResponseEntity<NewsListResponse> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(newsMapper
                        .newsListToNewsListResponse(newsService.findAll())
                );
    }

    @GetMapping("/filter")
    public ResponseEntity<NewsListResponse> getAllFilterBy(@Valid NewsFilter filter) {
        return ResponseEntity.ok(
                newsMapper.newsListToNewsListResponse(
                        newsService.filterBy(filter)
                )
        );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<NewsResponseWithComments> getById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(newsMapper.newsToResponseWithComments(
                        newsService.findById(id)
                ));
    }

    @PostMapping()
    public ResponseEntity<NewsResponseWithComments> create(@RequestBody @Valid NewsRequest newsRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newsMapper.newsToResponseWithComments(
                        newsService.save(
                                newsMapper.requestToNews(newsRequest)
                        )
                ));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<NewsResponseWithComments> update(@PathVariable("id") Long id,
                                                           @RequestBody @Valid NewsRequest newsRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(newsMapper.newsToResponseWithComments(
                        newsService.update(
                                id, newsMapper.requestToNews(id, newsRequest)
                        )
                ));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        newsService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
