package com.rest.hotelbooking.mapper;

import com.rest.hotelbooking.model.Reservation;
import com.rest.hotelbooking.web.dto.reservation.ReservationListResponse;
import com.rest.hotelbooking.web.dto.reservation.ReservationRequest;
import com.rest.hotelbooking.web.dto.reservation.ReservationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = RoomSimpleMapper.class)
public interface ReservationSimpleMapper extends SimpleRequestResponseMapper<Reservation, ReservationRequest, ReservationResponse> {
    @Override
    @Mapping(source = "roomId", target = "room.id")
    Reservation requestToModel(ReservationRequest request);

    @Override
    @Mapping(source = "room.id", target = "roomId")
    ReservationResponse modelToResponse(Reservation model);

    default ReservationListResponse modelListToModelListResponse(List<Reservation> model) {
        return new ReservationListResponse(
                modelListToResponseList(model)
        );
    }
}
