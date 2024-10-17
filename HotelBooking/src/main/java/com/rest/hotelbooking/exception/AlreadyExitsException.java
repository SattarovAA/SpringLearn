package com.rest.hotelbooking.exception;

public class AlreadyExitsException extends RuntimeException {
    public AlreadyExitsException(String message) {
        super(message);
    }
}
