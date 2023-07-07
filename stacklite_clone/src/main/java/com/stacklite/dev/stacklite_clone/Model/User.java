package com.stacklite.dev.stacklite_clone.Model;

import java.sql.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import lombok.AllArgsConstructor;
import lombok.Data;

@Table(name = "users")
@Entity
@Data
@AllArgsConstructor
public class User {
    @GeneratedValue
    @Column(name = "uuid", columnDefinition = "char(36)", nullable = false, updatable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID uuid;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String first_name;

    @Column(name = "last_name", nullable = false)
    private String last_name;

    @Column(name = "phonenumber")
    private Integer phonenumber;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "role")
    private Integer role;

    @Column(name = "stack")
    private String stack;

    @Column(name = "age")
    private String age;

    @Column(name = "nationality")
    private String nationality;

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "is_verified", columnDefinition = "DEFAULT false")
    private Boolean is_verified;

    @Column(name = "passwordResetToken")
    private String passwordResetToken;

    @Column(name = "profile_image")
    private String profile_image;

    @Column(name = "meta")
    private String meta;

    @Column(name = "createdAt", columnDefinition = "DEFAULT CURRENT_TIMESTAMP()")
    private Date createdAt;

    @Column(name = "updatedAt", columnDefinition = "DEFAULT CURRENT_TIMESTAMP()")
    private Date updatedAt;

    public User() {

    }

    @PrePersist
    protected void onCreate() {
        setUuid(java.util.UUID.randomUUID());
        setCreatedAt(new Date(java.lang.System.currentTimeMillis()));
        setUpdatedAt(new Date(java.lang.System.currentTimeMillis()));
        setIs_verified(false);
    }

    public User(
            String email,
            String username,
            String password,
            String first_name,
            String last_name,
            Integer phonenumber,
            Integer role,
            String stack,
            String age,
            String nationality,
            Boolean is_verified,
            String passwordResetToken,
            String profile_image,
            String meta) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phonenumber = phonenumber;
        this.role = role;
        this.stack = stack;
        this.age = age;
        this.nationality = nationality;
        this.profile_image = profile_image;
        this.is_verified = false;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Integer getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(Integer phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getIs_verified() {
        return is_verified;
    }

    public void setIs_verified(Boolean is_verified) {
        this.is_verified = is_verified;
    }

    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return "User{" +
                "uuid=" + uuid +
                ", id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", phonenumber=" + phonenumber +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", stack='" + stack + '\'' +
                ", age='" + age + '\'' +
                ", nationality='" + nationality + '\'' +
                ", userId=" + userId +
                ", is_verified=" + is_verified +
                ", passwordResetToken='" + passwordResetToken + '\'' +
                ", profile_image='" + profile_image + '\'' +
                ", meta='" + meta + '\'' +
                '}';
    }

}
