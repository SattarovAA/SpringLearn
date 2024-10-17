package com.rest.hotelbooking.mapper.statistic;

import com.rest.hotelbooking.model.statistic.RegistrationEvent;
import com.rest.hotelbooking.web.dto.statistic.RegistrationEventDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RegistrationEventMapper {
    RegistrationEvent dtoToModel(RegistrationEventDto model);

    RegistrationEventDto modelToDto(RegistrationEvent dto);
}
