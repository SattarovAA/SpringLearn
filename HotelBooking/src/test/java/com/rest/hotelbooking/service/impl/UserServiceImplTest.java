package com.rest.hotelbooking.service.impl;

import com.rest.hotelbooking.exception.EntityNotFoundException;
import com.rest.hotelbooking.model.entity.User;
import com.rest.hotelbooking.model.security.RoleType;
import com.rest.hotelbooking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserServiceImplTest Tests")
public class UserServiceImplTest {
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    private final int defaultPageSize = 10;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, passwordEncoder);
        try {
            Field PageSize = UserServiceImpl.class
                    .getDeclaredField("defaultPageSize");
            PageSize.setAccessible(true);
            PageSize.setInt(userService, defaultPageSize);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("findAll test: get all user data.")
    void givenWhenGetAllThenListUser() {
        List<User> userList = new ArrayList<>(List.of(
                new User(),
                new User()
        ));
        PageRequest pageRequest = PageRequest.of(0, defaultPageSize);

        when(userRepository.findAll(pageRequest))
                .thenReturn(new PageImpl<>(userList));

        List<User> actual = userService.findAll();

        assertEquals(userList.size(), actual.size());
        verify(userRepository, times(1))
                .findAll(pageRequest);
    }

    @Test
    @DisplayName("findById test: get user data by id.")
    void givenExistingIdWhenGetByIdThenUser() {
        Long userId = 1L;
        User defaultUser = new User(
                1L,
                "user",
                "pass",
                "email",
                Collections.singleton(RoleType.ROLE_USER),
                Collections.emptyList()
        );

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(defaultUser));

        User actual = userService.findById(userId);

        assertEquals(defaultUser, actual);
        verify(userRepository, times(1))
                .findById(any());
    }

    @Test
    @DisplayName("findById test: try to get user data by not existing id.")
    void givenNotExistingIdWhenGetByIdThenThrow() {
        Long userId = 1L;

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> userService.findById(userId),
                " index is incorrect."
        );
        verify(userRepository, times(1))
                .findById(any());
    }

    @Test
    @DisplayName("findByUsername test: get user data by name.")
    void givenExistingNameWhenGetByIdThenUser() {
        String userUsername = "user";
        User defaultUser = new User(
                1L,
                "user",
                "pass",
                "email",
                Collections.singleton(RoleType.ROLE_USER),
                Collections.emptyList()
        );

        when(userRepository.findByUsername(userUsername))
                .thenReturn(Optional.of(defaultUser));

        User actual = userService.findByUsername(userUsername);

        assertEquals(defaultUser, actual);
        verify(userRepository, times(1))
                .findByUsername(any());
    }

    @Test
    @DisplayName("findByUsername test: get user data by name.")
    void givenNotExistingNameWhenGetByIdThenUser() {
        String notExistName = "notExistName";

        when(userRepository.findByUsername(notExistName))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> userService.findByUsername(notExistName),
                "username is incorrect"
        );
        verify(userRepository, times(1))
                .findByUsername(any());
    }

    @Test
    @DisplayName("save test: send user data to repository.")
    void givenUserWhenSendUserToDbThenSavedUser() {
        String defaultPass = "pass";
        User userToSave = new User(
                null,
                "user",
                defaultPass,
                "email",
                Collections.singleton(RoleType.ROLE_USER),
                Collections.emptyList()
        );

        when(userRepository.save(userToSave))
                .thenReturn(userToSave);
        when(passwordEncoder.encode(defaultPass))
                .thenReturn(defaultPass);

        User actual = userService.save(userToSave);

        assertEquals(userToSave, actual);
        verify(userRepository, times(1))
                .save(any());
    }

    @Test
    @DisplayName("update test: send user data to repository.")
    void givenUserAndUserIdWhenSendUserToDbThenUpdatedUser() {
        String defaultPass = "pass";
        Long userId = 1L;
        User userToUpdate = new User(
                1L,
                "user",
                defaultPass,
                "email",
                Collections.singleton(RoleType.ROLE_USER),
                Collections.emptyList()
        );

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(userToUpdate));
        when(userRepository.save(userToUpdate))
                .thenReturn(userToUpdate);
        when(passwordEncoder.encode(defaultPass))
                .thenReturn(defaultPass);

        User actual = userService.update(userId, userToUpdate);

        assertEquals(userToUpdate, actual);
        verify(userRepository, times(1))
                .save(any());
        verify(userRepository, times(1))
                .findById(any());
    }

    @Test
    @DisplayName("update test: try update with not existed user id.")
    void givenUserAndNotExistedUserIdWhenSendUserToDbThenUpdatedUser() {
        String defaultPass = "pass";
        Long notExistedUserId = 1L;
        User userToUpdate = new User(
                1L,
                "user",
                defaultPass,
                "email",
                Collections.singleton(RoleType.ROLE_USER),
                Collections.emptyList()
        );

        when(userRepository.findById(notExistedUserId))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> userService.update(notExistedUserId, userToUpdate),
                "UserId is incorrect."
        );
        verify(userRepository, times(0))
                .save(any());
        verify(userRepository, times(1))
                .findById(any());
    }

    @Test
    @DisplayName("delete test: delete user data message to repository.")
    void givenExistedUserIdWhenSendUserToDbThenUpdatedUser() {
        Long existedUserId = 1L;

        when(userRepository.findById(existedUserId))
                .thenReturn(Optional.of(new User()));
        userService.deleteById(existedUserId);

        verify(userRepository, times(1))
                .findById(existedUserId);
        verify(userRepository, times(1))
                .deleteById(existedUserId);
    }

    @Test
    @DisplayName("delete test: delete user data message to repository.")
    void givenNotExistedUserIdWhenSendUserToDbThenUpdatedUser() {
        Long notExistedUserId = 1L;

        assertThrows(EntityNotFoundException.class,
                () -> userService.deleteById(notExistedUserId),
                "UserId is incorrect."
        );
        verify(userRepository, times(1))
                .findById(notExistedUserId);
        verify(userRepository, times(0))
                .deleteById(notExistedUserId);
    }
}
