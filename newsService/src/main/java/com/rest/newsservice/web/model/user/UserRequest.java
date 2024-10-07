package com.rest.newsservice.web.model.user;

import com.rest.newsservice.model.security.RoleType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRequest {
    @NotBlank(message = "Поле name должно быть заполнено!")
    private String username;
    @NotBlank(message = "Поле password должно быть заполнено!")
    private String password;
    private Set<RoleType> roles;
}
