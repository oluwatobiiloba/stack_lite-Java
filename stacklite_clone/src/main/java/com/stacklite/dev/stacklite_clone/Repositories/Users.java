package com.stacklite.dev.stacklite_clone.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stacklite.dev.stacklite_clone.Model.User;

public interface Users extends JpaRepository<User, Integer> {
    // Optional<User> findById(Integer id);
}
