package com.rest.newsservice.service.impl;

import com.rest.newsservice.aop.CheckUserIdPrivacy;
import com.rest.newsservice.aop.EntityType;
import com.rest.newsservice.exception.EntityNotFoundException;
import com.rest.newsservice.model.Category;
import com.rest.newsservice.model.Comment;
import com.rest.newsservice.model.News;
import com.rest.newsservice.model.User;
import com.rest.newsservice.repository.NewsRepository;
import com.rest.newsservice.repository.NewsSpecifications;
import com.rest.newsservice.service.CategoryService;
import com.rest.newsservice.service.UserService;
import com.rest.newsservice.web.filter.scopes.SessionHolder;
import com.rest.newsservice.service.NewsService;
import com.rest.newsservice.web.model.NewsFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final CategoryService categoryService;
    private final UserService userService;
    private final SessionHolder sessionHolder;

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
        enrich(model);
        return newsRepository.save(model);
    }

    @Override
    @CheckUserIdPrivacy(entityType = EntityType.NEWS)
    public News update(Long id, News model) {
        findById(id);

        enrich(model);
        return newsRepository.save(model);
    }

    @Override
    @CheckUserIdPrivacy(entityType = EntityType.NEWS)
    public void deleteById(Long id) {
        findById(id);
        newsRepository.deleteById(id);
    }

    @Override
    public void enrich(News requestNews) {
        Category currentCategory =
                categoryService.findByName(requestNews.getCategory().getName());
        UUID currentUserUuid = sessionHolder.logId();
        User currentUser = userService.findByUuid(currentUserUuid);
        List<Comment> comments = requestNews.getId() == null ?
                new ArrayList<>() :
                findById(requestNews.getId()).getComments();

        requestNews.setCategory(currentCategory);
        requestNews.setUser(currentUser);
        requestNews.setComments(comments);
    }
}
