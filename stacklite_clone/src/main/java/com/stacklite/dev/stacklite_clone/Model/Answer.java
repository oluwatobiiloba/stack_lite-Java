package com.stacklite.dev.stacklite_clone.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Table(name = "answers")
@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Answer {
    @Column(name = "uuid", columnDefinition = "uuid", nullable = false, updatable = false)
    private String uuid;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    @Column(name = "answer", nullable = false)
    private String answer;

    @Column(name = "downvotes", columnDefinition = "DEFAULT 0")
    private Integer downVotes;

    @Column(name = "accepted", columnDefinition = "DEFAULT false")
    private Boolean accepted;

    @Column(name = "upvotes", columnDefinition = "DEFAULT 0")
    private Integer upVotes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "questionId", referencedColumnName = "id", nullable = false)
    private Question question;

    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Voter> voters;

    private Timestamp updatedAt;

    private Timestamp createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
        this.uuid = java.util.UUID.randomUUID().toString();
    }
}
