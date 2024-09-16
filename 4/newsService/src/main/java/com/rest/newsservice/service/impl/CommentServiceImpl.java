package com.rest.newsservice.service.impl;

import com.rest.newsservice.aop.CheckUserIdPrivacy;
import com.rest.newsservice.aop.EntityType;
import com.rest.newsservice.exception.EntityNotFoundException;
import com.rest.newsservice.model.Comment;
import com.rest.newsservice.model.News;
import com.rest.newsservice.model.User;
import com.rest.newsservice.repository.CommentRepository;
import com.rest.newsservice.service.NewsService;
import com.rest.newsservice.service.UserService;
import com.rest.newsservice.web.filter.scopes.SessionHolder;
import com.rest.newsservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final NewsService newsService;
    private final SessionHolder sessionHolder;

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(
                EntityNotFoundException.create(
                        MessageFormat.format("Комментарий с id {0} не найден!", id)
                )
        );
    }

    @Override
    public Comment save(Comment model) {
        enrich(model);
        return commentRepository.save(model);
    }

    @Override
    @CheckUserIdPrivacy(entityType = EntityType.COMMENT)
    public Comment update(Long id, Comment model) {
        findById(id);

        model.setCreationTime(commentRepository.findById(id).orElseThrow()
                .getCreationTime());
        enrich(model);
        return commentRepository.save(model);
    }

    @Override
    @CheckUserIdPrivacy(entityType = EntityType.COMMENT)
    public void deleteById(Long id) {
        findById(id);
        commentRepository.deleteById(id);
    }

    @Override
    public void enrich(Comment requestComment) {
        News currentNews = newsService.findById(requestComment.getNews().getId());

        UUID currentUserUuid = sessionHolder.logId();
        User currentUser = userService.findByUuid(currentUserUuid);

        requestComment.setUser(currentUser);
        requestComment.setNews(currentNews);
    }
}
