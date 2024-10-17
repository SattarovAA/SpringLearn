package com.rest.hotelbooking.web.controller;

import com.rest.hotelbooking.mapper.UserSimpleMapper;
import com.rest.hotelbooking.service.impl.security.SecurityServiceImpl;
import com.rest.hotelbooking.web.dto.SimpleResponse;
import com.rest.hotelbooking.web.dto.user.UserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("api/auth")
@RestController
public class AuthController {
    private final SecurityServiceImpl securityServiceImpl;
    private final UserSimpleMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<SimpleResponse> registerUser(@RequestBody @Valid UserRequest userRequest) {
        securityServiceImpl.registerNewUser(userMapper.requestToModel(userRequest));

        return ResponseEntity.ok(new SimpleResponse("User created!"));
    }
}
