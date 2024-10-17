package com.rest.hotelbooking.web.controller.handler;

import lombok.Builder;

@Builder
public record ErrorResponseBody(
        String message,
        String description) {
}
