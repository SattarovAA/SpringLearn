package com.reactive.tasktracker.service.impl;

import java.util.Set;

import com.reactive.tasktracker.model.User;
import com.reactive.tasktracker.repository.UserRepository;
import com.reactive.tasktracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    public Mono<User> findById(String id) {
        return userRepository.findById(id);
    }

    public Mono<User> save(User user) {
        return userRepository.save(user);
    }

    public Mono<User> update(String id, User model) {
        return userRepository.findById(id).flatMap(modelToUpdate -> userRepository.save(
                new User(
                        id,
                        StringUtils.hasText(model.getUsername()) ? model.getUsername() : modelToUpdate.getUsername(),
                        StringUtils.hasText(model.getEmail()) ? model.getEmail() : modelToUpdate.getEmail()
                )));
    }

    public Mono<Void> deleteById(String id) {
        return userRepository.deleteById(id);
    }

    @Override
    public Mono<Void> deleteAll() {
        return userRepository.deleteAll();
    }

    @Override
    public Flux<User> findAllById(Set<String> userIds) {
        return userRepository.findAllById(userIds);
    }

}
