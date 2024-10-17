package com.rest.hotelbooking.mapper.statistic;

import com.rest.hotelbooking.model.statistic.ReservationEvent;
import com.rest.hotelbooking.web.dto.statistic.ReservationEventDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservationEventMapper {
    ReservationEvent dtoToModel(ReservationEventDto model);

    ReservationEventDto modelToDto(ReservationEvent dto);
}
