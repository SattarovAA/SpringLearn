package com.rest.hotelbooking.model.security;

import com.rest.hotelbooking.model.entity.User;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * UserDetails realization with {@link User} entity.
 * {@link #isAccountNonExpired()} always true.
 * {@link #isAccountNonLocked()} always true.
 * {@link #isCredentialsNonExpired()} always true.
 * {@link #isEnabled()} always true.
 */
@EqualsAndHashCode
@RequiredArgsConstructor
public class AppUserDetails implements UserDetails {
    /**
     * Entity to implement UserDetails.
     */
    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles()
                .stream()
                .map(rt -> new SimpleGrantedAuthority(rt.name()))
                .toList();
    }

    /**
     * User.id getter.
     *
     * @return User.id
     */
    public Long getUserId() {
        return user.getId();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
