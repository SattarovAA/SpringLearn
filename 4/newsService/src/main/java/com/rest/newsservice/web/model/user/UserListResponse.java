package com.rest.newsservice.web.model.user;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserListResponse {
    List<UserResponse> users = new ArrayList<>();
}
