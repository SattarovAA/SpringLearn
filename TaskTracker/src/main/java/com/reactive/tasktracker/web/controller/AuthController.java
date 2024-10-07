package com.reactive.tasktracker.web.controller;

import com.reactive.tasktracker.mapper.UserMapper;
import com.reactive.tasktracker.service.impl.security.SecurityService;
import com.reactive.tasktracker.web.model.user.UserRequest;
import com.reactive.tasktracker.web.model.SimpleResponse;
import com.reactive.tasktracker.web.model.security.AuthResponse;
import com.reactive.tasktracker.web.model.security.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RequestMapping("api/auth")
@RestController
public class AuthController {
    private final UserMapper userMapper;
    private final SecurityService securityService;

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody LoginRequest loginRequest) {
        return securityService.authenticationUser(loginRequest);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<SimpleResponse> registerUser(@RequestBody UserRequest userRequest) {
        return securityService.registerNewUser(userMapper.requestToModel(userRequest))
                .then(Mono.just(new SimpleResponse("User created!")));
    }
}
