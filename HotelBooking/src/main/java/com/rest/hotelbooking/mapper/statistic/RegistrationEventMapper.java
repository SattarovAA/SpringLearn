package com.rest.hotelbooking.mapper.statistic;

import com.rest.hotelbooking.model.dto.statistic.RegistrationEventDto;
import com.rest.hotelbooking.model.entity.User;
import com.rest.hotelbooking.model.entity.statistic.RegistrationEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for {@link RegistrationEvent} entity.
 *
 * @see RegistrationEventDto
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RegistrationEventMapper {
    /**
     * {@link RegistrationEventDto} to {@link RegistrationEvent} mapping.
     *
     * @param dto {@link RegistrationEventDto} for mapping.
     * @return mapped {@link RegistrationEvent}.
     */
    RegistrationEvent dtoToModel(RegistrationEventDto dto);

    /**
     * {@link RegistrationEvent} to {@link RegistrationEventDto} mapping.
     *
     * @param model {@link RegistrationEvent} for mapping.
     * @return mapped {@link RegistrationEventDto}.
     */
    RegistrationEventDto modelToDto(RegistrationEvent model);
    @Mapping(source = "user.id", target = "userId")
    RegistrationEventDto userToEventDto(User user);
}
