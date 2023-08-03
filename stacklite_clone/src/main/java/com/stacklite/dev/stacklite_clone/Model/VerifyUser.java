package com.stacklite.dev.stacklite_clone.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Table(name = "verify_users")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "verification_token", nullable = false)
    private String verificationToken;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "verification_timestamp")
    private Date verificationTimestamp;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
}
