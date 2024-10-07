package com.reactive.tasktracker.web.controller;

import com.reactive.tasktracker.mapper.UserMapper;
import com.reactive.tasktracker.service.UserService;
import com.reactive.tasktracker.web.model.user.UserRequest;
import com.reactive.tasktracker.web.model.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER')")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Flux<UserResponse> getAll() {
        return userService.findAll().map(userMapper::modelToResponse);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER')")
    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserResponse>> getUserById(@PathVariable("id") String id) {
        return userService.findById(id)
                .map(userMapper::modelToResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build()) ;
    }
    @PreAuthorize("hasRole('USER') or hasRole('MANAGER')")
    @PutMapping("/{id}")
    public Mono<ResponseEntity<UserResponse>> update(@PathVariable("id") String id,
                                                     @RequestBody UserRequest userRequest) {
        return userService.update(id, userMapper.requestToModel(id, userRequest))
                .map(userMapper::modelToResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER')")
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteById(@PathVariable("id") String id) {
        return userService.deleteById(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER')")
    @DeleteMapping(path = "")
    public Mono<ResponseEntity<Void>> deleteAll() {
        return userService.deleteAll()
                .then(Mono.just(ResponseEntity.noContent().build()));
    }
}
