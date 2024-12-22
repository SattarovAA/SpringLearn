package com.rest.hotelbooking.controller;

import com.rest.hotelbooking.mapper.UserMapper;
import com.rest.hotelbooking.model.dto.user.UserRequest;
import com.rest.hotelbooking.model.entity.User;
import com.rest.hotelbooking.model.security.RoleType;
import com.rest.hotelbooking.service.security.SecurityService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("AuthControllerTest tests")
public class AuthControllerTest {
    @MockitoBean
    private SecurityService securityService;
    @MockitoBean
    private UserMapper userMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithAnonymousUser
    @DisplayName("registerUser test: new admin user from anonymous user.")
    void givenUserRequestWhenRegisterUrlThenMessage() throws Exception {
        String url = "/api/auth/register";
        String requestJson = """
                {
                "username": "user",
                "password": "pass",
                "email": "email",
                "roles": ["ROLE_ADMIN"]
                }""";
        User userToRegister = new User(
                1L,
                "user",
                "pass",
                "email",
                Collections.singleton(RoleType.ROLE_ADMIN),
                Collections.emptyList()
        );
        when(userMapper.requestToModel(any(UserRequest.class)))
                .thenReturn(userToRegister);
        when(securityService.registerNewUser(userToRegister))
                .thenReturn(userToRegister);

        mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                )
                .andExpect(jsonPath("$.message").isString())
                .andExpect(status().isCreated());
        verify(securityService, times(1))
                .registerNewUser(any());
    }
}
