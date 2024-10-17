package com.rest.hotelbooking.web.dto.room;

import java.util.List;

public record RoomListResponse(
        List<RoomResponse> rooms
) {
}
