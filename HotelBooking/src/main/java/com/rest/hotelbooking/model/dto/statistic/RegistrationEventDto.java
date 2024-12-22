package com.rest.hotelbooking.model.dto.statistic;

/**
 * DTO for working with entity RegistrationEvent.
 *
 * @param userId id registered user.
 */
public record RegistrationEventDto(
        Long userId
) {
}
