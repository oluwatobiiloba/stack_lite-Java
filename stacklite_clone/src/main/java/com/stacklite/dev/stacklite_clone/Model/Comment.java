package com.stacklite.dev.stacklite_clone.Model;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "comments")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Comment {

    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid", columnDefinition = "char(36)", nullable = false, updatable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
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
