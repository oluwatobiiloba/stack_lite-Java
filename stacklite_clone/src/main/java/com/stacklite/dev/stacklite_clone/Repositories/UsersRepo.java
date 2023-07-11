package com.stacklite.dev.stacklite_clone.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.stacklite.dev.stacklite_clone.Model.User;

public interface UsersRepo extends JpaRepository<User, Integer> {
    // Optional<User> findById(Integer id);
    Optional<List<User>> findByisVerified(Boolean isVerified);

    Optional<List<User>> findByUsername(String username);

    Optional<List<User>> findByStack(String stack);

    Optional<List<User>> findByEmail(String email);

    Optional<List<User>> findByUsernameLike(String likePattern);
}
