package com.reactive.tasktracker.model;

import com.reactive.tasktracker.model.security.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "user_col")
public class User {

    @Id
    private String id;
    private String username;
    private String email;
    private String password;

    @Field("roles")
    private Set<RoleType> roles = new HashSet<>();
}
