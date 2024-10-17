package com.rest.hotelbooking.service.impl;

import com.rest.hotelbooking.exception.AlreadyExitsException;
import com.rest.hotelbooking.exception.DeleteEntityWithReferenceException;
import com.rest.hotelbooking.exception.EntityNotFoundException;
import com.rest.hotelbooking.model.User;
import com.rest.hotelbooking.repository.UserRepository;
import com.rest.hotelbooking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

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
        model = enrich(model);
        checkDuplicateUsername(model.getUsername());
        checkDuplicateEmail(model.getEmail());
        return userRepository.save(model);
    }

    @Override
    public User update(Long id, User model) {
        model = enrich(model, id);
        checkDuplicateUsername(model.getUsername(), model.getId());
        checkDuplicateEmail(model.getEmail(), model.getId());
        return userRepository.save(model);
    }

    private User enrich(User model) {
        return User.builder()
                .username(model.getUsername())
                .password(passwordEncoder.encode(model.getPassword()))
                .roles(model.getRoles())
                .email(model.getEmail())
                .reservations(List.of())
                .build();
    }

    private User enrich(User model, Long id) {
        User userToUpdate = findById(id);
        return User.builder()
                .id(id)
                .username(model.getUsername() == null
                        ? userToUpdate.getUsername()
                        : model.getUsername())
                .password(model.getPassword() == null
                        ? passwordEncoder.encode(userToUpdate.getPassword())
                        : passwordEncoder.encode(model.getPassword()))
                .roles(model.getRoles() == null
                        ? userToUpdate.getRoles()
                        : model.getRoles())
                .email(model.getEmail() == null
                        ? userToUpdate.getEmail()
                        : model.getEmail())
                .reservations(userToUpdate.getReservations())
                .build();
    }

    private void checkDuplicateUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new AlreadyExitsException("Username already exist!");
        }
    }

    private void checkDuplicateUsername(String username, Long currentUserId) {
        if (userRepository.existsByUsernameAndIdNot(username, currentUserId)) {
            throw new AlreadyExitsException("Username already exist!");
        }
    }

    private void checkDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new AlreadyExitsException("Email already exist!");
        }
    }

    private void checkDuplicateEmail(String email, Long currentUserId) {
        if (userRepository.existsByEmailAndIdNot(email, currentUserId)) {
            throw new AlreadyExitsException("Email already exist!");
        }
    }

    @Override
    public void deleteById(Long id) {
        User userToDelete = findById(id);
        checkReservationReference(userToDelete);
        userRepository.deleteById(id);
    }

    private void checkReservationReference(User model) {
        if (!model.getReservations().isEmpty()) {
            throw new DeleteEntityWithReferenceException(
                    MessageFormat.format(
                            "Unable to delete user with id {0}. User have {1} reservations",
                            model.getId(), model.getReservations().size())
            );
        }
    }
}
