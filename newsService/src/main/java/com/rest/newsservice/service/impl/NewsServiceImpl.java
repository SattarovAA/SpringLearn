package com.rest.newsservice.service.impl;

import com.rest.newsservice.exception.EntityNotFoundException;
import com.rest.newsservice.model.News;
import com.rest.newsservice.model.security.AppUserDetails;
import com.rest.newsservice.repository.NewsRepository;
import com.rest.newsservice.repository.NewsSpecifications;
import com.rest.newsservice.service.CategoryService;
import com.rest.newsservice.service.UserService;
import com.rest.newsservice.service.NewsService;
import com.rest.newsservice.web.model.NewsFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final CategoryService categoryService;
    private final UserService userService;

    @Override
    public List<News> findAll() {
        return newsRepository.findAll();
    }

    @Override
    public List<News> filterBy(NewsFilter filter) {
        return newsRepository.findAll(NewsSpecifications.withFilter(filter),
                PageRequest.of(filter.getPageNumber(), filter.getPageSize())
        ).getContent();
    }

    @Override
    public News findById(Long id) {
        return newsRepository.findById(id).orElseThrow(
                EntityNotFoundException.create(
                        MessageFormat.format("Новость с id {0} не найдена!", id)
                )
        );
    }

    @Override
    public News save(News model) {
        model = enrich(model);
        return newsRepository.save(model);
    }

    @Override
    public News update(Long id, News model) {
        findById(id);
        model = enrich(model);
        return newsRepository.save(model);
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        newsRepository.deleteById(id);
    }

    public News enrich(News requestNews) {
        AppUserDetails userDetails = (AppUserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        return News.builder()
                .id(requestNews.getId())
                .content(requestNews.getContent())
                .title(requestNews.getTitle())
                .category(categoryService.findByName(requestNews.getCategory().getName()))
                .user(userService.findById(userDetails.getUserId()))
                .comments(requestNews.getId() == null ?
                        new ArrayList<>() :
                        findById(requestNews.getId()).getComments())
                .build();
    }
}
