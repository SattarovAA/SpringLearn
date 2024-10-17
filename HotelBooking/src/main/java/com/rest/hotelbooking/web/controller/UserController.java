package com.rest.hotelbooking.web.controller;

import com.rest.hotelbooking.mapper.UserSimpleMapper;
import com.rest.hotelbooking.service.UserService;
import com.rest.hotelbooking.web.dto.user.UserRequest;
import com.rest.hotelbooking.web.dto.user.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final UserSimpleMapper userMapper;

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userMapper.modelToResponse(
                        userService.findById(id)
                ));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping(path = "/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable("id") Long id,
                                               @RequestBody @Valid UserRequest modelRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userMapper.modelToResponse(
                        userService.update(
                                id, userMapper.requestToModel(id, modelRequest)
                        )
                ));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
