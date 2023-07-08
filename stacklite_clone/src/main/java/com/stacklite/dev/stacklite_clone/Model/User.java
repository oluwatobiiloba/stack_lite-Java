package com.stacklite.dev.stacklite_clone.Model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Table(name = "users")
@Entity
@Data
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "uuid", columnDefinition = "uuid", nullable = false, updatable = false)
    private String uuid;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phonenumber", nullable = false)
    private Long phoneNumber;

    @Column(name = "email", nullable = false)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false)
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "passwordResetToken")
    private String passwordResetToken;

    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified;

    @Column(name = "role", nullable = false)
    private Integer role;

    @Column(name = "stack")
    private String stack;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "meta")
    private String meta;

    @Column(name = "age")
    private String age;

    @Column(name = "nationality")
    private String nationality;

    private String userId;

    @Column(name = "createdAt")
    private Date createdAt;

    @Column(name = "updatedAt")
    private Date updatedAt;

    // Constructors, getters, and setters

    public User() {
    }

    public User(String uuid, String username, String firstName, String lastName, Long phoneNumber, String email,
            String password, String passwordResetToken, Boolean isVerified, Integer role, String stack,
            String profileImage, String meta, String age, String nationality) {
        this.uuid = uuid;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.passwordResetToken = passwordResetToken;
        this.isVerified = isVerified;
        this.role = role;
        this.stack = stack;
        this.profileImage = profileImage;
        this.meta = meta;
        this.age = age;
        this.nationality = nationality;
    }

    // Other methods

    @PrePersist
    protected void onCreate() {
        this.uuid = java.util.UUID.randomUUID().toString();
        this.createdAt = new Date(java.lang.System.currentTimeMillis());
        this.updatedAt = new Date(java.lang.System.currentTimeMillis());
        this.isVerified = false;
    }

    @Override
    public String toString() {
        return "User{" +
                "uuid=" + uuid +
                ", id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", first_name='" + firstName + '\'' +
                ", last_name='" + lastName + '\'' +
                ", phonenumber=" + phoneNumber +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", stack='" + stack + '\'' +
                ", age='" + age + '\'' +
                ", nationality='" + nationality + '\'' +
                ", userId=" + userId +
                ", is_verified=" + isVerified +
                ", passwordResetToken='" + passwordResetToken + '\'' +
                ", profile_image='" + profileImage + '\'' +
                ", meta='" + meta + '\'' +
                '}';
    }

}
