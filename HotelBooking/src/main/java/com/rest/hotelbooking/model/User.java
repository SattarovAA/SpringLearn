package com.rest.hotelbooking.model;

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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@FieldNameConstants
@Builder
@Entity
@Table(name = "users",uniqueConstraints = @UniqueConstraint(columnNames = User.Fields.username))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = Fields.id)
    private Long id;
    @Column(name = Fields.username)
    private String username;
    @Column(name = Fields.password)
    private String password;
    @Column(name = Fields.email)
    private String email;
    @ElementCollection(targetClass = RoleType.class, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<RoleType> roles = new HashSet<>();
    @OneToMany(mappedBy = Reservation.Fields.user, fetch = FetchType.EAGER)
    @Builder.Default
    private List<Reservation> reservations = new ArrayList<>();
}

