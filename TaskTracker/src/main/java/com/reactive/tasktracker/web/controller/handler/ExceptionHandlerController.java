package com.reactive.tasktracker.web.controller.handler;

import com.reactive.tasktracker.exception.EntityNotFoundException;
import com.reactive.tasktracker.exception.AlreadyExitsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;


@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController
{

    @ExceptionHandler(AlreadyExitsException.class)
    public Mono<ResponseEntity<ErrorResponse>> alreadyExitsHandler(AlreadyExitsException ex) {
        log.info("AlreadyExitsException: " + ex.getLocalizedMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getLocalizedMessage())));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public Mono<ResponseEntity<ErrorResponse>> entityNotFoundHandler(EntityNotFoundException ex) {
        log.info("EntityNotFoundException: " + ex.getLocalizedMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getLocalizedMessage())));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public Mono<ResponseEntity<ErrorResponse>> badCredentialsHandler(BadCredentialsException ex) {
        log.info("BadCredentialsException: " + ex.getLocalizedMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getLocalizedMessage())));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Mono<ResponseEntity<ErrorResponse>> forbidden(AccessDeniedException ex) {
        log.info("AccessDeniedException: " + ex.getLocalizedMessage());
        return Mono.just(ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(ex.getLocalizedMessage())));
    }
}
