package com.stacklite.dev.stacklite_clone.Repositories;

import com.stacklite.dev.stacklite_clone.Model.BlobContainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlobContainersRepo extends JpaRepository<BlobContainer, Integer> {

}
