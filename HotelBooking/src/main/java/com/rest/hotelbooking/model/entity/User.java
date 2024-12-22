package com.rest.hotelbooking.model.entity;

import com.rest.hotelbooking.model.security.RoleType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.util.*;

/**
 * Entity User.
 * Entity for working with JpaRepository.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@FieldNameConstants
@Builder
@Entity
@Table(name = "users",
//        uniqueConstraints = @UniqueConstraint(columnNames = User.Fields.username),
        indexes = {
                @Index(columnList = User.Fields.username, unique = true),
                @Index(columnList = User.Fields.email, unique = true)
        })
public class User {
    /**
     * Long User @Id for JpaRepository.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = Fields.id)
    private Long id;
    /**
     * User name.
     */
    @Column(name = Fields.username)
    private String username;
    /**
     * User password.
     */
    @Column(name = Fields.password)
    private String password;
    /**
     * User email.
     */
    @Column(name = Fields.email)
    private String email;
    /**
     * User authorization role type.
     *
     * @value new HashSet<>()
     */
    @ElementCollection(targetClass = RoleType.class, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<RoleType> roles = new HashSet<>();
    /**
     * User reservations.
     *
     * @value new ArrayList<>()
     */
    @OneToMany(mappedBy = Reservation.Fields.user, fetch = FetchType.EAGER)
    @Builder.Default
    private List<Reservation> reservations = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id)
                && Objects.equals(username, user.username)
                && Objects.equals(password, user.password)
                && Objects.equals(email, user.email)
                && Objects.equals(roles, user.roles)
                && Objects.equals(reservations.size(), user.reservations.size());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id, username, password, email,
                roles, reservations.size()
        );
    }
}

