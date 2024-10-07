package com.rest.newsservice.exception.security;

public class AlreadyExitsException extends RuntimeException {
    public AlreadyExitsException(String message) {
        super(message);
    }
}
