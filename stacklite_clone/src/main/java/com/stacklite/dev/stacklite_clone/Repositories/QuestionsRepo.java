package com.stacklite.dev.stacklite_clone.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stacklite.dev.stacklite_clone.Model.Question;

@Repository
public interface QuestionsRepo extends JpaRepository<Question, Integer> {

}
