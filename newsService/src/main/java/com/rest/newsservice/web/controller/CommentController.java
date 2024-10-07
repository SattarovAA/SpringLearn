package com.rest.newsservice.web.controller;

import com.rest.newsservice.aop.CheckUserIdPrivacy;
import com.rest.newsservice.aop.EntityType;
import com.rest.newsservice.mapper.CommentMapper;
import com.rest.newsservice.model.security.RoleType;
import com.rest.newsservice.service.CommentService;
import com.rest.newsservice.web.model.comment.CommentListResponse;
import com.rest.newsservice.web.model.comment.CommentRequest;
import com.rest.newsservice.web.model.comment.CommentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @GetMapping()
    @PreAuthorize("hasAnyRole('USER','MODERATOR','ADMIN')")
    public ResponseEntity<CommentListResponse> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentMapper.commentListToCommentListResponse(
                        commentService.findAll())
                );
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAnyRole('USER','MODERATOR','ADMIN')")
    public ResponseEntity<CommentResponse> getById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentMapper.commentToResponse(
                        commentService.findById(id)
                ));
    }

    @PostMapping()
    @PreAuthorize("hasAnyRole('USER','MODERATOR','ADMIN')")
    public ResponseEntity<CommentResponse> create(@RequestBody @Valid CommentRequest commentRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentMapper.commentToResponse(
                        commentService.save(
                                commentMapper.requestToComment(commentRequest)
                        )
                ));
    }

    @PutMapping(path = "/{id}")
    @PreAuthorize("hasAnyRole('USER','MODERATOR','ADMIN')")
    @CheckUserIdPrivacy(entityType = EntityType.COMMENT,
            alwaysAccessRoles = {})
    public ResponseEntity<CommentResponse> update(@PathVariable("id") Long id,
                                                  @RequestBody @Valid CommentRequest commentRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentMapper.commentToResponse(
                        commentService.update(
                                id,
                                commentMapper.requestToComment(id, commentRequest)
                        )
                ));
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAnyRole('USER','MODERATOR','ADMIN')")
    @CheckUserIdPrivacy(entityType = EntityType.COMMENT,
            alwaysAccessRoles = {
                    RoleType.ROLE_ADMIN,
                    RoleType.ROLE_MODERATOR})
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        commentService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
