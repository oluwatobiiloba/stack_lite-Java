package com.stacklite.dev.stacklite_clone.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "questions")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Question {

    @Column(name = "uuid", columnDefinition = "uuid", nullable = false, updatable = false)
    private String uuid;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "status", nullable = false)
    private Integer status;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    @PrePersist
    protected void onCreate() {
        this.uuid = java.util.UUID.randomUUID().toString();
    }
}
