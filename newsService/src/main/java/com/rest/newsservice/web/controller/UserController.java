package com.rest.newsservice.web.controller;

import com.rest.newsservice.mapper.UserMapper;
import com.rest.newsservice.service.UserService;
import com.rest.newsservice.web.model.user.UserListResponse;
import com.rest.newsservice.web.model.user.UserRequest;
import com.rest.newsservice.web.model.user.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping()
    public ResponseEntity<UserListResponse> getAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userMapper.userListToUserListResponse(userService.findAll()));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userMapper.userToResponse(userService.findById(id)));
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userMapper.userToResponse(
                        userService.save(
                                userMapper.requestToUser(userRequest)
                        )
                ));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable("id") Long id,
                                               @RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userMapper.userToResponse(
                        userService.update(
                                id, userMapper.requestToUser(id, userRequest)
                        )
                ));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

