package com.stacklite.dev.stacklite_clone.Repositories;

import com.stacklite.dev.stacklite_clone.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepo extends JpaRepository<User, Integer> {
    Optional<User> findById(Integer id);

    Page<User> findByisVerified(Boolean isVerified, Pageable pageable);

    Optional<User> findByUsername(String username);

    Page<User> findByStack(String stack, Pageable pageable);

    Page<User> findByAge(String age, Pageable pageable);

    Optional<User> findByEmail(String email);

    Page<User> findByUsernameLike(String likePattern, Pageable pageable);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findByPasswordResetToken(String token);

    @Query("SELECT u FROM User u WHERE " +
            "(:username IS NULL OR u.username LIKE %:username%) AND " +
            "(:phoneNumber IS NULL OR u.phoneNumber =:phoneNumber ) AND " +
            "(:lastName IS NULL OR u.lastName =:lastName ) AND " +
            "(:firstName IS NULL OR u.firstName =:firstName ) AND " +
            "(:stack IS NULL OR u.stack =:stack ) AND " +
            "(:email IS NULL OR u.email =:email ) AND " +
            "(:id IS NULL OR u.id =:id) ")
    Page<User> findByParams(
            @Param("username") String username,
            @Param("phoneNumber") Integer phoneNumber,
            @Param("id") Integer id,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("stack") String stack,
            @Param("email") String email,
            Pageable pageable
    );
}
