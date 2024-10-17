package com.rest.hotelbooking.repository;

import com.rest.hotelbooking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

//    boolean existsById(Long id);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByUsernameAndIdNot(String username, Long id);
}
