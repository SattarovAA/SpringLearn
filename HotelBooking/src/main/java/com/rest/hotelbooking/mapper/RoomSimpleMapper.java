package com.rest.hotelbooking.mapper;

import com.rest.hotelbooking.model.Room;
import com.rest.hotelbooking.web.dto.room.RoomListResponse;
import com.rest.hotelbooking.web.dto.room.RoomRequest;
import com.rest.hotelbooking.web.dto.room.RoomResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = ReservationSimpleMapper.class)
public interface RoomSimpleMapper extends SimpleRequestResponseMapper<Room, RoomRequest, RoomResponse> {
    @Override
    @Mapping(source = "hotelId", target = "hotel.id")
    Room requestToModel(RoomRequest request);

    @Override
    @Mapping(source = "hotel.id", target = "hotelId")
    RoomResponse modelToResponse(Room model);

    default RoomListResponse modelListToModelListResponse(List<Room> model) {
        return new RoomListResponse(modelListToResponseList(model));
    }
}
