package com.rest.hotelbooking.web.controller.handler;

import com.rest.hotelbooking.exception.AlreadyExitsException;
import com.rest.hotelbooking.exception.DeleteEntityWithReferenceException;
import com.rest.hotelbooking.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> badRequest(MethodArgumentNotValidException ex) {
        log.error("not valid argument: " + ex.getLocalizedMessage());
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errorMessages = bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        String errorMessage = String.join("; ", errorMessages);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errorMessage));
    }

    @ExceptionHandler(DeleteEntityWithReferenceException.class)
    public ResponseEntity<ErrorResponseBody> badRequest(DeleteEntityWithReferenceException ex, WebRequest webRequest) {
        log.error(ex.getLocalizedMessage());
        return buildResponse(HttpStatus.BAD_REQUEST, ex, webRequest);
    }

    @ExceptionHandler(AlreadyExitsException.class)
    public ResponseEntity<ErrorResponseBody> badRequest(AlreadyExitsException ex, WebRequest webRequest) {
        log.error(ex.getLocalizedMessage());
        return buildResponse(HttpStatus.BAD_REQUEST, ex, webRequest);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseBody> notFound(EntityNotFoundException ex, WebRequest webRequest) {
        log.error(ex.getLocalizedMessage());
        return buildResponse(HttpStatus.NOT_FOUND, ex, webRequest);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseBody> forbidden(AccessDeniedException ex, WebRequest webRequest) {
        log.error(ex.getLocalizedMessage());
        return buildResponse(HttpStatus.FORBIDDEN, ex, webRequest);
    }

    private ResponseEntity<ErrorResponseBody> buildResponse(HttpStatus status, Exception ex, WebRequest webRequest) {
        return ResponseEntity.status(status)
                .body(ErrorResponseBody.builder()
                        .message(ex.getMessage())
                        .description(webRequest.getDescription(false))
                        .build());
    }
}
