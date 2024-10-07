package com.rest.newsservice.model;

import com.rest.newsservice.model.security.RoleType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldNameConstants
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = Fields.id)
    private Long id;
    @Column(name = Fields.username)
    private String username;
    @Column(name = Fields.password)
    private String password;
    @ElementCollection(targetClass = RoleType.class, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<RoleType> roles = new HashSet<>();

    @OneToMany(mappedBy = News.Fields.user, fetch = FetchType.LAZY)
    private List<News> news;
    @OneToMany(mappedBy = Comment.Fields.user, fetch = FetchType.LAZY)
    private List<Comment> comments;
}
