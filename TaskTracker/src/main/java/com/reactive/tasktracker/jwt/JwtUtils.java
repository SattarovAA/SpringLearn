package com.reactive.tasktracker.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

import static java.util.stream.Collectors.joining;

@Component
@Slf4j
public class JwtUtils {
    @Value("${app.jwt.secretKey}")
    private String jwtSecret;
    @Value("${app.jwt.tokenExpiration}")
    private Duration tokenExpiration;
    private static final String AUTHORITIES_KEY = "roles";


    public String createToken(Authentication authentication) {

        String username = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication
                .getAuthorities();
        Claims claims = Jwts.claims().setSubject(username);
        if (!authorities.isEmpty()) {
            claims.put(AUTHORITIES_KEY, authorities.stream()
                    .map(GrantedAuthority::getAuthority).collect(joining(",")));
        }

        Date now = new Date();
        Date expiry = new Date(now.getTime() + tokenExpiration.toMillis());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public Boolean validation(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("Token was expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Token is unsupported: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid token: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("Claims string is empty: {}", e.getMessage());
        } catch (JwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            log.trace("Invalid JWT token trace.", e);
        }
        return false;
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody();

        Object authoritiesClaim = claims.get(AUTHORITIES_KEY);

        Collection<? extends GrantedAuthority> authorities =
                authoritiesClaim == null
                        ? AuthorityUtils.NO_AUTHORITIES
                        : AuthorityUtils
                        .commaSeparatedStringToAuthorityList(authoritiesClaim.toString());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
}
