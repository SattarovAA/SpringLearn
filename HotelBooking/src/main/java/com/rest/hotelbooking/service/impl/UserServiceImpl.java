package com.rest.hotelbooking.service.impl;

import com.rest.hotelbooking.exception.AlreadyExitsException;
import com.rest.hotelbooking.exception.DeleteEntityWithReferenceException;
import com.rest.hotelbooking.exception.EntityNotFoundException;
import com.rest.hotelbooking.model.entity.User;
import com.rest.hotelbooking.repository.UserRepository;
import com.rest.hotelbooking.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

/**
 * Service for working with entity {@link User}.
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    /**
     * {@link User} Repository.
     */
    private final UserRepository userRepository;
    /**
     * Default PasswordEncoder.
     * Needed to define and update the field password in {@link User}.
     *
     * @see #enrich(User)
     * @see #enrich(User, Long)
     */
    private final PasswordEncoder passwordEncoder;
    /**
     * Default page size.
     *
     * @see #findAll()
     */
    @Value("${app.service.user.defaultPageSize}")
    private int defaultPageSize;
    /**
     * Default page number.
     *
     * @see #findAll()
     */
    @Value("${app.service.user.defaultPageNumber}")
    private int defaultPageNumber;

    /**
     * {@link PageRequest} of type {@link User} with
     * {@link #defaultPageNumber} and {@link #defaultPageSize} parameters.
     *
     * @return list of type {@link User}.
     */
    @Override
    public List<User> findAll() {
        log.info("Try to get all users without filter.");
        return userRepository.findAll(
                PageRequest.of(defaultPageNumber, defaultPageSize)
        ).getContent();
    }

    /**
     * Get a {@link User} object by specifying its id.
     *
     * @param id id searched {@link User}.
     * @return object of type {@link User} with searched id.
     * @throws EntityNotFoundException if {@link User} with id not found.
     */
    @Override
    public User findById(Long id) {
        log.info("Find user with id {}.", id);
        return userRepository.findById(id).orElseThrow(
                EntityNotFoundException.create(
                        MessageFormat.format(
                                "User with id {0} not found!",
                                id
                        )
                )
        );
    }

    /**
     * Find {@link User} with User.username equals username.
     *
     * @param username username searched User.
     * @return object of type {@link User} with searched id.
     * @throws EntityNotFoundException if {@link User} with username not found.
     */
    @Override
    public User findByUsername(String username) {
        log.info("Find user with username {}.", username);
        return userRepository.findByUsername(username).orElseThrow(
                EntityNotFoundException.create(
                        MessageFormat.format(
                                "User with username {0} not found!",
                                username
                        )
                )
        );
    }

    /**
     * Save object model of type {@link User}.
     *
     * @param model object of type {@link User} to save.
     * @return object of type {@link User} that was saved.
     */
    @Override
    public User save(User model) {
        log.warn("Try to create new user.");
        model = enrich(model);
        checkDuplicateUsername(model.getUsername());
        checkDuplicateEmail(model.getEmail());
        return userRepository.save(model);
    }
    /**
     * Update object model of type {@link User} with T.id equals id.
     *
     * @param id    id of the object to be updated.
     * @param model object of type {@link User} to update.
     * @return object of type {@link User} that was updated.
     */
    @Override
    public User update(Long id, User model) {
        log.warn("Try to update user with id {}.", id);
        model = enrich(model, id);
        checkDuplicateUsername(model.getUsername(), model.getId());
        checkDuplicateEmail(model.getEmail(), model.getId());
        return userRepository.save(model);
    }

    /**
     * Enrich model to full version.
     *
     * @param model {@link User} to enrich.
     * @return {@link User} with all fields.
     */
    private User enrich(User model) {
        return User.builder()
                .username(model.getUsername())
                .password(passwordEncoder.encode(model.getPassword()))
                .roles(model.getRoles())
                .email(model.getEmail())
                .reservations(List.of())
                .build();
    }

    /**
     * Enrich model to full version.
     * If the model has no field values, then the values are taken
     * from a previously existing entity with the same id.
     *
     * @param model   {@link User} with partially updated fields.
     * @param modelId user id to update {@link User}.
     * @return Updated {@link User}.
     */
    private User enrich(User model, Long modelId) {
        User userToUpdate = findById(modelId);
        return User.builder()
                .id(modelId)
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

    /**
     * Check duplicate username.
     * For save new {@link User}.
     *
     * @param username username to check.
     * @throws AlreadyExitsException if username already exist.
     */
    private void checkDuplicateUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new AlreadyExitsException(
                    MessageFormat.format(
                            "Username ({0}) already exist!",
                            username
                    )
            );
        }
    }

    /**
     * Check duplicate username.
     * For update {@link User}.
     *
     * @param username      username to check.
     * @param currentUserId current user id.
     * @throws AlreadyExitsException if username already exist
     *                               excluding {@link User} with currentUserId.
     */
    private void checkDuplicateUsername(String username, Long currentUserId) {
        if (userRepository.existsByUsernameAndIdNot(username, currentUserId)) {
            throw new AlreadyExitsException(
                    MessageFormat.format(
                            "Username ({0}) already exist!",
                            username
                    )
            );
        }
    }

    /**
     * Check duplicate email.
     * For save new {@link User}.
     *
     * @param email email to check.
     * @throws AlreadyExitsException if email already exist.
     */
    private void checkDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new AlreadyExitsException(
                    MessageFormat.format(
                            "Email ({0}) already exist!",
                            email
                    )
            );
        }
    }

    /**
     * Check duplicate email.
     * For update {@link User}.
     *
     * @param email         email to check.
     * @param currentUserId current user id.
     * @throws AlreadyExitsException if email already exist
     *                               excluding {@link User} with currentUserId.
     */
    private void checkDuplicateEmail(String email, Long currentUserId) {
        if (userRepository.existsByEmailAndIdNot(email, currentUserId)) {
            throw new AlreadyExitsException(
                    MessageFormat.format(
                            "Email ({0}) already exist!",
                            email
                    )
            );
        }
    }
    /**
     * Delete object with User.id equals id from database.
     *
     * @param id id of the object to be deleted.
     */
    @Override
    public void deleteById(Long id) {
        log.warn("Try to delete user with id {}.", id);
        User userToDelete = findById(id);
        checkReservationReference(userToDelete);
        userRepository.deleteById(id);
    }

    /**
     * Check reservation reference.
     *
     * @param model {@link User} to check.
     * @throws DeleteEntityWithReferenceException - if {@link User#getReservations()} not empty.
     */
    private void checkReservationReference(User model) {
        if (!model.getReservations().isEmpty()) {
            throw new DeleteEntityWithReferenceException(
                    MessageFormat.format(
                            "Unable to delete user with id {0}. User have {1} reservations",
                            model.getId(),
                            model.getReservations().size()
                    )
            );
        }
    }
}
