package com.rest.hotelbooking.mapper;

import com.rest.hotelbooking.model.dto.reservation.ReservationListResponse;
import com.rest.hotelbooking.model.dto.reservation.ReservationRequest;
import com.rest.hotelbooking.model.dto.reservation.ReservationResponse;
import com.rest.hotelbooking.model.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper for working with {@link Reservation} entity.
 * <br>
 * Uses {@link RoomMapper} for mapping inner Reservation.room field.
 *
 * @see ReservationResponse
 * @see ReservationRequest
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = RoomMapper.class)
public interface ReservationMapper {
    /**
     * {@link ReservationRequest} to {@link Reservation} mapping.
     * Map ReservationRequest.roomId to Reservation.room.id.
     *
     * @param request {@link ReservationRequest} for mapping.
     * @return mapped {@link Reservation}.
     */
    @Mapping(source = "roomId", target = "room.id")
    Reservation requestToModel(ReservationRequest request);

    /**
     * {@link Reservation} to {@link ReservationResponse} mapping.
     * Map Reservation.room.id to ReservationResponse.roomId.
     *
     * @param model {@link Reservation} for mapping.
     * @return mapped {@link ReservationResponse}.
     */
    @Mapping(source = "room.id", target = "roomId")
    ReservationResponse modelToResponse(Reservation model);

    /**
     * List of {@link Reservation} to
     * list of {@link ReservationResponse} mapping.
     *
     * @param modelList List of {@link Reservation} for mapping.
     * @return mapped List of {@link ReservationResponse}.
     * @see #modelToResponse(Reservation)
     */
    List<ReservationResponse> modelListToResponseList(List<Reservation> modelList);

    /**
     * List of {@link Reservation} to {@link ReservationListResponse} mapping.
     *
     * @param modelList List of {@link Reservation} for mapping.
     * @return mapped {@link ReservationListResponse}.
     * @see #modelListToResponseList(List)
     */
    default ReservationListResponse modelListToModelListResponse(
            List<Reservation> modelList) {
        return new ReservationListResponse(
                modelListToResponseList(modelList)
        );
    }
}
