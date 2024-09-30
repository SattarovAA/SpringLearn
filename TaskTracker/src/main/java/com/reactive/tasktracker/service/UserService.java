package com.reactive.tasktracker.service;

import com.reactive.tasktracker.model.User;
import reactor.core.publisher.Flux;

import java.util.Set;

public interface UserService extends CrudService<User> {
    Flux<User> findAllById(Set<String> userIds);
}
