package com.stacklite.dev.stacklite_clone.Repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stacklite.dev.stacklite_clone.Model.User;

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
}
