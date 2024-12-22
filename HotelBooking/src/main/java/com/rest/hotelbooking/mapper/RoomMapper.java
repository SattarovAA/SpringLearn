package com.rest.hotelbooking.mapper;

import com.rest.hotelbooking.model.entity.Room;
import com.rest.hotelbooking.model.dto.room.RoomListResponse;
import com.rest.hotelbooking.model.dto.room.RoomRequest;
import com.rest.hotelbooking.model.dto.room.RoomResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper for working with {@link Room} entity.
 * <br>
 * Uses {@link ReservationMapper} for mapping inner Room.reservations field.
 *
 * @see RoomResponse
 * @see RoomRequest
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = ReservationMapper.class)
public interface RoomMapper {
    /**
     * {@link RoomRequest} to {@link Room} mapping.
     * Map RoomRequest.hotelId to Room.hotel.id.
     *
     * @param request {@link RoomRequest} for mapping.
     * @return mapped {@link Room}.
     */
    @Mapping(source = "hotelId", target = "hotel.id")
    Room requestToModel(RoomRequest request);


    /**
     * {@link Room} to {@link RoomResponse} mapping.
     * Map Room.hotel.id to RoomResponse.hotelId.
     *
     * @param model {@link Room} for mapping.
     * @return mapped {@link RoomResponse}.
     */
    @Mapping(source = "hotel.id", target = "hotelId")
    RoomResponse modelToResponse(Room model);

    /**
     * List of {@link Room} to List of {@link RoomResponse} mapping.
     *
     * @param modelList List of {@link Room} for mapping.
     * @return mapped List of {@link RoomResponse}.
     */
    List<RoomResponse> modelListToResponseList(List<Room> modelList);

    /**
     * List of {@link Room} to {@link RoomListResponse} mapping.
     *
     * @param modelList List of {@link Room} for mapping.
     * @return mapped {@link RoomListResponse}.
     * @see #modelListToResponseList(List)
     */
    default RoomListResponse modelListToModelListResponse(List<Room> modelList) {
        return new RoomListResponse(modelListToResponseList(modelList));
    }
}
