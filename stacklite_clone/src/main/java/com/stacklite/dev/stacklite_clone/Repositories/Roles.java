package com.stacklite.dev.stacklite_clone.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Roles extends JpaRepository<Roles, Integer> {

}
