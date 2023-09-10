package com.stacklite.dev.stacklite_clone.Model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "comments")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Comment {

    @Column(name = "uuid", columnDefinition = "uuid", nullable = false, updatable = false)
    private String uuid;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "answerId", nullable = false)
    private Answer answer;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(name = "comment", nullable = false)
    private String comment;

    @PrePersist
    protected void onCreate() {
        this.uuid = java.util.UUID.randomUUID().toString();
    }
}
