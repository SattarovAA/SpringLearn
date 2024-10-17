package com.rest.hotelbooking.mapper;

import com.rest.hotelbooking.model.User;
import com.rest.hotelbooking.web.dto.user.UserRequest;
import com.rest.hotelbooking.web.dto.user.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserSimpleMapper extends SimpleRequestResponseMapper<User, UserRequest, UserResponse> {
}
