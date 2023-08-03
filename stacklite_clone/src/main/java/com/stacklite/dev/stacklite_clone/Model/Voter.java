package com.stacklite.dev.stacklite_clone.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "voters")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Voter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "answerId", nullable = false)
    private Answer answer;

    @Column(name = "userId", nullable = false)
    private Integer userId;

    @Column(name = "upvotes", nullable = false)
    private Boolean upvotes;

    @Column(name = "downvotes", nullable = false)
    private Boolean downvotes;
}
