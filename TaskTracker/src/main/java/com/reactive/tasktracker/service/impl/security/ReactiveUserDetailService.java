package com.reactive.tasktracker.service.impl.security;

import com.reactive.tasktracker.model.security.AppUserDetails;
import com.reactive.tasktracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ReactiveUserDetailService implements ReactiveUserDetailsService {
    private final UserService userService;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userService.findByUsername(username)
                .flatMap(Mono::just)
                .map(AppUserDetails::new);
    }
}
