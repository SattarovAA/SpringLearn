package com.rest.newsservice.service.impl;

import com.rest.newsservice.exception.EntityNotFoundException;
import com.rest.newsservice.model.Comment;
import com.rest.newsservice.model.security.AppUserDetails;
import com.rest.newsservice.repository.CommentRepository;
import com.rest.newsservice.service.NewsService;
import com.rest.newsservice.service.UserService;
import com.rest.newsservice.web.filter.scopes.SessionHolder;
import com.rest.newsservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

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
        model = enrich(model);
        return commentRepository.save(model);
    }

    @Override
    public Comment update(Long id, Comment model) {
        findById(id);

        model.setCreationTime(findById(id).getCreationTime());
        model = enrich(model);
        return commentRepository.save(model);
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        commentRepository.deleteById(id);
    }

    public Comment enrich(Comment requestComment) {
        AppUserDetails userDetails = (AppUserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        return Comment.builder()
                .id(requestComment.getId())
                .content(requestComment.getContent())
                .creationTime(requestComment.getCreationTime())
                .updatedTime(requestComment.getUpdatedTime())
                .news(newsService.findById(requestComment.getNews().getId()))
                .user(userService.findById(userDetails.getUserId()))
                .build();
    }
}
