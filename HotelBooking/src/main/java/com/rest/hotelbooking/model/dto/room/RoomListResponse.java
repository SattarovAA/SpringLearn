package com.rest.hotelbooking.model.dto.room;

import java.util.List;

/**
 * List Response DTO for working with entity room.
 *
 * @param rooms list of {@link RoomResponse}.
 */
public record RoomListResponse(
        List<RoomResponse> rooms
) {
}
