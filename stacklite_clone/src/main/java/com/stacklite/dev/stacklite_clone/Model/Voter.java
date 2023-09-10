package com.stacklite.dev.stacklite_clone.Model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "voters")
@Entity
@Getter
@Setter
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
