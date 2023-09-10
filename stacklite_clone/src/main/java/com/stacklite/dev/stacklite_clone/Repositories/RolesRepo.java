package com.stacklite.dev.stacklite_clone.Repositories;

import com.stacklite.dev.stacklite_clone.Model.ERole;
import com.stacklite.dev.stacklite_clone.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepo extends JpaRepository<Role, Long> {
    // Role findByUserIdAndRole(Integer userId, String role);
    Optional<Role> findByName(ERole name);
}
