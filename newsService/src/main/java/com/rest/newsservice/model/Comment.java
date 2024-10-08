package com.rest.newsservice.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldNameConstants
@Builder
@Entity
@Table(name = "comments")
public class Comment{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "content")
    private String content;
    @CreationTimestamp
    @Column(name = "creation_time")
    private Instant creationTime;
    @UpdateTimestamp
    @Column(name = "updated_time")
    private Instant updatedTime;
    @ManyToOne()
    @JoinColumn(name = "news_id", referencedColumnName = "id")
    private News news;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
