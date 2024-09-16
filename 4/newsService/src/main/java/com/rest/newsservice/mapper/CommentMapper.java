package com.rest.newsservice.mapper;

import com.rest.newsservice.model.Comment;
import com.rest.newsservice.web.model.comment.CommentListResponse;
import com.rest.newsservice.web.model.comment.CommentRequest;
import com.rest.newsservice.web.model.comment.CommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
    @Mapping(source = "commentRequest.newsId", target = "news.id")
    Comment requestToComment(CommentRequest commentRequest);

    @Mapping(source = "commentRequest.newsId", target = "news.id")
    @Mapping(source = "commentId", target = "id")
    Comment requestToComment(Long commentId, CommentRequest commentRequest);

    @Mapping(source = "user.name", target = "userName")
    CommentResponse commentToResponse(Comment comment);

    List<CommentResponse> commentListToCommentResponseList(List<Comment> comments);

    default CommentListResponse commentListToCommentListResponse(List<Comment> comments) {
        CommentListResponse response = new CommentListResponse();
        response.setComments(commentListToCommentResponseList(comments));
        return response;
    }
}
