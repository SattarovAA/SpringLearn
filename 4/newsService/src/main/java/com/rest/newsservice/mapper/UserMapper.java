package com.rest.newsservice.mapper;

import com.rest.newsservice.model.User;
import com.rest.newsservice.web.model.user.UserListResponse;
import com.rest.newsservice.web.model.user.UserRequest;
import com.rest.newsservice.web.model.user.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    User requestToUser(UserRequest userRequest);

    @Mapping(source = "userId", target = "id")
    User requestToUser(Long userId, UserRequest userRequest);

    UserResponse userToResponse(User user);

    List<UserResponse> userListToUserResponseList(List<User> users);

    default UserListResponse userListToUserListResponse(List<User> users) {
        UserListResponse response = new UserListResponse();
        response.setUsers(userListToUserResponseList(users));
        return response;
    }
}
