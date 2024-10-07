package com.rest.newsservice.service.impl;

import com.rest.newsservice.exception.EntityNotFoundException;
import com.rest.newsservice.exception.security.AlreadyExitsException;
import com.rest.newsservice.model.User;
import com.rest.newsservice.repository.UserRepository;
import com.rest.newsservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                EntityNotFoundException.create(
                        MessageFormat.format("Пользователь с id {0} не найден!", id)
                )
        );
    }
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                EntityNotFoundException.create(
                        MessageFormat.format("Пользователь с username {0} не найден!", username)
                )
        );
    }
    @Override
    public User save(User model) {
        checkDuplicateUsername(model.getUsername());
        return userRepository.save(User.builder()
                .username(model.getUsername())
                .password(passwordEncoder.encode(model.getPassword()))
                .roles(model.getRoles())
                .build()
        );
    }

    @Override
    public User update(Long id, User model) {
        findById(id);
        return userRepository.save(User.builder()
                .id(id)
                .username(model.getUsername())
                .password(passwordEncoder.encode(model.getPassword()))
                .roles(model.getRoles())
                .build());
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        userRepository.deleteById(id);
    }

    private void checkDuplicateUsername(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new AlreadyExitsException("Username already exist!");
        }
    }
}
