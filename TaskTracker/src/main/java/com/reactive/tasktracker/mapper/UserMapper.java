package com.reactive.tasktracker.mapper;

import com.reactive.tasktracker.model.User;
import com.reactive.tasktracker.web.model.request.UserRequest;
import com.reactive.tasktracker.web.model.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User requestToModel(UserRequest userRequest);

    User requestToModel(String userId, UserRequest userRequest);

    UserResponse modelToResponse(User user);

}
