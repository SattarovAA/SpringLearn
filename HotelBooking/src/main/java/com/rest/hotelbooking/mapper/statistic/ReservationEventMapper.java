package com.rest.hotelbooking.mapper.statistic;

import com.rest.hotelbooking.model.dto.reservation.ReservationResponse;
import com.rest.hotelbooking.model.entity.Reservation;
import com.rest.hotelbooking.model.entity.statistic.ReservationEvent;
import com.rest.hotelbooking.model.dto.statistic.ReservationEventDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for {@link ReservationEvent} entity.
 *
 * @see ReservationEventDto
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservationEventMapper {
    /**
     * {@link ReservationEventDto} to {@link ReservationEvent} mapping.
     *
     * @param dto {@link ReservationEventDto} for mapping.
     * @return mapped {@link ReservationEvent}.
     */
    ReservationEvent dtoToModel(ReservationEventDto dto);

    /**
     * {@link ReservationEvent} to {@link ReservationEventDto} mapping.
     *
     * @param model {@link ReservationEvent} for mapping.
     * @return mapped {@link ReservationEventDto}.
     */
    ReservationEventDto modelToDto(ReservationEvent model);
    /**
     * {@link Reservation} to {@link ReservationEventDto} mapping.
     * Map Reservation.user.id to ReservationEventDto.userId.
     *
     * @param reservation {@link Reservation} for mapping.
     * @return mapped {@link ReservationEventDto}.
     */
    @Mapping(source = "user.id", target = "userId")
    ReservationEventDto reservationToEventDto(Reservation reservation);
}
