package com.stacklite.dev.stacklite_clone.Repositories;

import com.stacklite.dev.stacklite_clone.Model.Voter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotersRepo extends JpaRepository<Voter, Integer> {

}
