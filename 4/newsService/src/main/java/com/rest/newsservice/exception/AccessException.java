package com.rest.newsservice.exception;

public class AccessException extends RuntimeException {
    public AccessException(String message) {
        super(message);
    }
}
