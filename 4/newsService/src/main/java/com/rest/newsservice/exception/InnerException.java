package com.rest.newsservice.exception;

public class InnerException extends RuntimeException{
    public InnerException(String message) {
        super(message);
    }
}
