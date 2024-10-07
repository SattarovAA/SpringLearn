package com.reactive.tasktracker.service.impl.security;

import com.reactive.tasktracker.jwt.JwtUtils;
import com.reactive.tasktracker.model.User;
import com.reactive.tasktracker.service.UserService;
import com.reactive.tasktracker.web.model.security.AuthResponse;
import com.reactive.tasktracker.web.model.security.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class SecurityService {
    private final ReactiveAuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public Mono<ResponseEntity<AuthResponse>> authenticationUser(LoginRequest loginRequest) {
        return Mono.just(loginRequest)
                .flatMap(login -> authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(
                                login.getUsername(), login.getPassword()))
                        .map(jwtUtils::createToken))
                .map(jwt -> {
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
                    AuthResponse tokenBodyTrue = AuthResponse.builder()
                            .token(jwt)
                            .username(loginRequest.getUsername())
                            .build();
                    return new ResponseEntity<>(tokenBodyTrue, httpHeaders, HttpStatus.OK);
                });
    }
    public Mono<User> registerNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.checkDuplicateUsername(user).flatMap(userService::save);
    }
}
