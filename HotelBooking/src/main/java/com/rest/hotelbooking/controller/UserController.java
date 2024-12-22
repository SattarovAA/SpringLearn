package com.rest.hotelbooking.controller;

import com.rest.hotelbooking.mapper.UserMapper;
import com.rest.hotelbooking.model.dto.user.UserRequest;
import com.rest.hotelbooking.model.dto.user.UserResponse;
import com.rest.hotelbooking.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for working with entity user.
 *
 * @see UserRequest
 * @see UserResponse
 */
@Tag(name = "UserController",
        description = "Controller for working with users.")
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {
    /**
     * Service for working with entity user.
     */
    private final UserService userService;
    /**
     * Mapper for working with entity user.
     */
    private final UserMapper userMapper;

    /**
     * Get a User object by specifying its id.
     * The response is User object with
     * id, username, password, email, list roles.
     *
     * @param id the id of the user to retrieve.
     * @return {@link ResponseEntity} with {@link HttpStatus#OK}
     * and {@link UserResponse} with searched id.
     */
    @Operation(
            summary = "Get user by id.",
            description = "Get a User object by specifying its id. " +
                    "The response is User object with " +
                    "id, username, password, email, list roles.",
            tags = {"user", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = UserResponse.class))
            }),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "401")
    })
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable("id") Long id) {
        log.info("Try to get user with id " + id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userMapper.modelToResponse(userService.findById(id)));
    }

    /**
     * Update user with id by {@link UserRequest}.
     *
     * @param id           user id to update user.
     * @param modelRequest {@link UserRequest} to update user.
     * @return {@link ResponseEntity} with {@link HttpStatus#OK}
     * and {@link UserResponse} by updated user.
     */
    @Operation(
            summary = "Update user by specifying its id.",
            tags = {"user", "put"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = UserResponse.class))
            }),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "401"),
    })
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping(path = "/{id}")
    public ResponseEntity<UserResponse> update(
            @PathVariable("id") Long id,
            @RequestBody @Valid UserRequest modelRequest
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userMapper.modelToResponse(
                                userService.update(
                                        id,
                                        userMapper.requestToModel(modelRequest)
                                )
                        )
                );
    }

    /**
     * Delete user by id.
     *
     * @param id id User to delete user.
     * @return {@link ResponseEntity} with {@link HttpStatus#NO_CONTENT}.
     */
    @Operation(
            summary = "Delete user by specifying its id.",
            tags = {"user", "delete"})
    @ApiResponses({
            @ApiResponse(responseCode = "204"),
            @ApiResponse(responseCode = "400"),
            @ApiResponse(responseCode = "401"),
    })
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
