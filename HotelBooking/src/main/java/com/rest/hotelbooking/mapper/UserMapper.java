package com.rest.hotelbooking.mapper;

import com.rest.hotelbooking.model.entity.User;
import com.rest.hotelbooking.model.dto.user.UserRequest;
import com.rest.hotelbooking.model.dto.user.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for working with {@link User} entity.
 *
 * @see UserResponse
 * @see UserRequest
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    /**
     * {@link UserRequest} to {@link User} mapping.
     *
     * @param request {@link UserRequest} for mapping.
     * @return mapped {@link User}.
     */
    User requestToModel(UserRequest request);

    /**
     * {@link User} to {@link UserResponse} mapping.
     *
     * @param model {@link User} for mapping.
     * @return mapped {@link UserResponse}.
     */
    UserResponse modelToResponse(User model);
}
