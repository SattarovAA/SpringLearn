package com.rest.hotelbooking.controller;

import com.rest.hotelbooking.mapper.UserMapper;
import com.rest.hotelbooking.service.security.SecurityService;
import com.rest.hotelbooking.model.dto.util.SimpleResponse;
import com.rest.hotelbooking.model.dto.user.UserRequest;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Authentication Controller for new user registration.
 *
 * @see UserRequest
 */
@Tag(name = "AuthenticationController",
        description = "User authentication controller.")
@RequiredArgsConstructor
@RequestMapping("api/auth")
@RestController
@Slf4j
public class AuthController {
    /**
     * Service for work with new user.
     */
    private final SecurityService securityService;
    /**
     * Mapper for user entity.
     */
    private final UserMapper userMapper;

    /**
     * Map {@link UserRequest} to user in {@link UserMapper} and register it.
     *
     * @param userRequest {@link UserRequest} for registration.
     * @return {@link ResponseEntity} with {@link SimpleResponse} if created.
     */
    @Operation(
            summary = "Register new User.",
            tags = {"auth", "post", "register", "public"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = SimpleResponse.class))
            }),
            @ApiResponse(responseCode = "400"),
    })
    @PostMapping("/register")
    public ResponseEntity<SimpleResponse> registerUser(
            @RequestBody @Valid UserRequest userRequest) {
        securityService.registerNewUser(
                userMapper.requestToModel(userRequest)
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SimpleResponse("User created!"));
    }
}
