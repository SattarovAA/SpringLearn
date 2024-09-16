package com.rest.newsservice.service.impl;

import com.rest.newsservice.aop.CheckUserIdPrivacy;
import com.rest.newsservice.aop.EntityType;
import com.rest.newsservice.exception.DuplicateKeyException;
import com.rest.newsservice.exception.EntityNotFoundException;
import com.rest.newsservice.model.User;
import com.rest.newsservice.repository.UserRepository;
import com.rest.newsservice.web.filter.scopes.SessionHolder;
import com.rest.newsservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final SessionHolder sessionHolder;

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

    public User findByUuid(UUID uuid) {
        return userRepository.findByUuid(uuid).orElseThrow(
                EntityNotFoundException.create(
                        MessageFormat.format("Пользователь с uuid {0} не найден!", uuid)
                )
        );
    }

    @Override
    public User save(User model) {
        UUID userUuid = sessionHolder.logId();
        checkDuplicateUuid(userUuid);
        model.setUuid(userUuid);
        return userRepository.save(model);
    }

    @Override
    @CheckUserIdPrivacy(entityType = EntityType.USER)
    public User update(Long id, User model) {
        findById(id);
        model.setUuid(sessionHolder.logId());
        return userRepository.save(model);
    }

    @Override
    @CheckUserIdPrivacy(entityType = EntityType.USER)
    public void deleteById(Long id) {
        findById(id);
        userRepository.deleteById(id);
    }

    private void checkDuplicateUuid(UUID uuid) {
        if (userRepository.findByUuid(uuid).isPresent()) {
            throw new DuplicateKeyException(
                    "Пользователь с этим UUID уже существует!"
            );
        }
    }
}
