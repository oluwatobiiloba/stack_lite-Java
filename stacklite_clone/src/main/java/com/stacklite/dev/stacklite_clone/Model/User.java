package com.stacklite.dev.stacklite_clone.Model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stacklite.dev.stacklite_clone.Utils.PasswordUtils;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "createdAt")
    private Date createdAt;

    @Column(name = "updatedAt")
    private Date updatedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> authorities = new ArrayList<>();

    // @Column(name = "authorities")
    // private Date authorities;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().toString()))
                .collect(Collectors.toList());
    }

    @PrePersist
    protected void onCreate() {
        this.uuid = java.util.UUID.randomUUID().toString();
        this.createdAt = new Date(java.lang.System.currentTimeMillis());
        this.updatedAt = new Date(java.lang.System.currentTimeMillis());
        this.isVerified = false;
        this.password = PasswordUtils.hashPassword(this.password);
    }

}
