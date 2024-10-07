package com.reactive.tasktracker.service;

import com.reactive.tasktracker.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface UserService extends CrudService<User> {
    Flux<User> findAllById(Set<String> userIds);

    Mono<User> findByUsername(String username);

    Mono<User> checkDuplicateUsername(User user);
}
