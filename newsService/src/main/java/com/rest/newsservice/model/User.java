package com.rest.newsservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldNameConstants
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "uuid")
    private UUID uuid;
    @OneToMany(mappedBy = News.Fields.user, fetch = FetchType.LAZY)
    private List<News> news;
    @OneToMany(mappedBy = Comment.Fields.user, fetch = FetchType.LAZY)
    private List<Comment> comments;
}
