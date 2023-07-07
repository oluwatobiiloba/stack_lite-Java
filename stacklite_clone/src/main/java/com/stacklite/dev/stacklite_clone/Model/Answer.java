package com.stacklite.dev.stacklite_clone.Model;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "answers")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid", columnDefinition = "char(36)", nullable = false, updatable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String uuid;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    @Column(name = "answers", nullable = false)
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

    // Add the necessary getters and setters

    // Define relationships with Comments and Voters
    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Voter> voters;

    @PrePersist
    protected void onCreate() {
        this.uuid = java.util.UUID.randomUUID().toString();
    }
}
