package com.reactive.tasktracker.web.model.user;

import com.reactive.tasktracker.model.security.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRequest {
    private String id;
    private String username;
    private String email;
    private String password;
    private Set<RoleType> roles = new HashSet<>();
}
