package com.rest.hotelbooking.controller;

import com.rest.hotelbooking.mapper.UserMapper;
import com.rest.hotelbooking.model.dto.user.UserRequest;
import com.rest.hotelbooking.model.dto.user.UserResponse;
import com.rest.hotelbooking.model.entity.User;
import com.rest.hotelbooking.model.security.RoleType;
import com.rest.hotelbooking.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("UserControllerTest tests")
public class UserControllerTest {
    private final static String urlTemplate = "/api/user";
    @MockitoBean
    private UserService userService;
    @MockitoBean
    private UserMapper userMapper;
    @Autowired
    private MockMvc mockMvc;
    private final User defaultUser = new User(
            1L,
            "user",
            "pass",
            "email",
            Collections.singleton(RoleType.ROLE_USER),
            Collections.emptyList()
    );
    private final UserResponse simpleUserResponse = new UserResponse(
            1L,
            "user",
            "pass",
            "email",
            Collections.singleton(RoleType.ROLE_USER)
    );

    @Test
    @WithAnonymousUser
    @DisplayName("getById test: get user data by id from anonymous user.")
    void givenAnonymousUserWhenGetByIdUrlThenStatusUnauthorized()
            throws Exception {
        String getByIdUrl = urlTemplate + "/1";

        mockMvc.perform(get(getByIdUrl))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser()
    @DisplayName("getById test: get user data by user id.")
    void givenUserIdWhenGetByIdUrlThenUserResponse()
            throws Exception {
        String getByIdUrl = urlTemplate + "/1";

        when(userService.findById(1L))
                .thenReturn(defaultUser);
        when(userMapper.modelToResponse(defaultUser))
                .thenReturn(simpleUserResponse);

        mockMvc.perform(get(getByIdUrl))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.username").isString())
                .andExpect(jsonPath("$.password").isString())
                .andExpect(jsonPath("$.email").isString())
                .andExpect(jsonPath("$.roles").isArray())
                .andExpect(status().isOk());

        verify(userMapper, times(1))
                .modelToResponse(any());
        verify(userService, times(1))
                .findById(1L);
    }

    @Test
    @WithAnonymousUser
    @DisplayName("update test: update user data by id from anonymous user.")
    void givenAnonymousUserWhenUpdateUrlThenStatusUnauthorized()
            throws Exception {
        String updateUrl = urlTemplate + "/1";

        mockMvc.perform(put(updateUrl))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("update test: update user data by id from admin user.")
    void givenUserIdWhenUpdateUrlThenUserResponse()
            throws Exception {
        String updateUrl = urlTemplate + "/1";
        String requestUserJson = """
                {
                   "username": "user",
                   "password": "pass",
                   "email" : "email",
                   "roles": ["ROLE_ADMIN"]
                }""";
        UserRequest correctRequest = new UserRequest(
                "user",
                "pass",
                "email",
                Collections.singleton(RoleType.ROLE_ADMIN)
        );

        when(userMapper.requestToModel(correctRequest))
                .thenReturn(defaultUser);
        when(userService.update(1L, defaultUser))
                .thenReturn(defaultUser);
        when(userMapper.modelToResponse(defaultUser))
                .thenReturn(simpleUserResponse);

        mockMvc.perform(put(updateUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestUserJson)
                )
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.username").isString())
                .andExpect(jsonPath("$.password").isString())
                .andExpect(jsonPath("$.email").isString())
                .andExpect(jsonPath("$.roles").isArray())
                .andExpect(status().isOk());

        verify(userMapper, times(1))
                .modelToResponse(any());
        verify(userMapper, times(1))
                .requestToModel(any());
        verify(userService, times(1))
                .update(any(), any());
    }

    @Test
    @WithAnonymousUser
    @DisplayName("delete test: delete user data by id from anonymous user.")
    void givenAnonymousUserWhenDeleteUrlThenStatusUnauthorized()
            throws Exception {
        String deleteUrl = urlTemplate + "/1";

        mockMvc.perform(delete(deleteUrl))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("delete test: delete user data by id from admin user.")
    void givenAdminUserWhenDeleteUrlThenStatusNoContent()
            throws Exception {
        String deleteUrl = urlTemplate + "/1";

        mockMvc.perform(delete(deleteUrl))
                .andExpect(status().isNoContent());
        verify(userService, times(1))
                .deleteById(any());
    }
}

