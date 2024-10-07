package com.reactive.tasktracker.service.impl;

import java.text.MessageFormat;
import java.util.Set;

import com.reactive.tasktracker.exception.AlreadyExitsException;
import com.reactive.tasktracker.model.User;
import com.reactive.tasktracker.repository.UserRepository;
import com.reactive.tasktracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    public Mono<User> findById(String id) {
        return userRepository.findById(id);
    }

    public Mono<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Mono<User> save(User user) {
        return userRepository.save(user);
    }

    public Mono<User> checkDuplicateUsername(User user) {
        return findByUsername(user.getUsername())
                .hasElement()
                .flatMap(res -> {
                    if (res) {
                        return Mono.empty();
                    }
                    return Mono.just(user);
                })
                .switchIfEmpty(Mono.error(
                        new AlreadyExitsException(
                                MessageFormat.format("Username {0} already exist!",
                                        user.getUsername()
                                )
                        ))
                );
    }

    public Mono<User> update(String id, User model) {
        return userRepository.findById(id).flatMap(modelToUpdate -> userRepository.save(
                User.builder()
                        .id(id)
                        .username(StringUtils.hasText(model.getUsername())
                                ? model.getUsername()
                                : modelToUpdate.getUsername())
                        .password(StringUtils.hasText(model.getPassword())
                                ? passwordEncoder.encode(model.getPassword())
                                : modelToUpdate.getPassword())
                        .email(StringUtils.hasText(model.getEmail())
                                ? model.getEmail()
                                : modelToUpdate.getEmail())
                        .roles(model.getRoles() != null
                                ? model.getRoles()
                                : modelToUpdate.getRoles())
                        .build()
        ));
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
