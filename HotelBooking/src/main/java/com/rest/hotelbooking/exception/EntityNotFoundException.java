package com.rest.hotelbooking.exception;

import java.util.function.Supplier;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public static Supplier<EntityNotFoundException> create(String message) {
        return () -> new EntityNotFoundException(message);
    }
}
