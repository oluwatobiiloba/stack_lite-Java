package com.stacklite.dev.stacklite_clone.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stacklite.dev.stacklite_clone.Model.ERole;
import com.stacklite.dev.stacklite_clone.Model.Role;

public interface RolesRepo extends JpaRepository<Role, Long> {
    // Role findByUserIdAndRole(Integer userId, String role);
    Optional<Role> findByName(ERole name);
}
