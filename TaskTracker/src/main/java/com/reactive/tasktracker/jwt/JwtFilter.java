package com.reactive.tasktracker.jwt;

import com.reactive.tasktracker.service.impl.security.ReactiveUserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter implements WebFilter {
    private final ReactiveUserDetailService userDetailService;
    private final JwtUtils jwtUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange webExchange,
                             WebFilterChain webFilterChain) {
        try {
            String jwtToken = getToken(webExchange.getRequest());
            if (jwtToken != null && jwtUtils.validation(jwtToken)) {
                Mono<UserDetails> principal =
                        userDetailService.findByUsername(jwtUtils.getUsernameFromJwtToken(jwtToken));

                Mono<UsernamePasswordAuthenticationToken> authentication = principal.map(pr ->
                        new UsernamePasswordAuthenticationToken(pr.getUsername(), jwtToken, pr.getAuthorities()));

                return authentication
                        .subscribeOn(Schedulers.boundedElastic())
                        .flatMap(auth -> webFilterChain.filter(webExchange)
                                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth)));

            }
        } catch (Exception e) {
            log.error("cannot set user authentication: {}", e.getMessage());
        }
        return webFilterChain.filter(webExchange);
    }

    private String getToken(ServerHttpRequest request) {
        String headerAuth = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
