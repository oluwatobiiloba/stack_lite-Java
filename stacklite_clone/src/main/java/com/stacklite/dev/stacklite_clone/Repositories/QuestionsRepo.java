package com.stacklite.dev.stacklite_clone.Repositories;

import com.stacklite.dev.stacklite_clone.Model.Answer;
import com.stacklite.dev.stacklite_clone.Model.Question;
import com.stacklite.dev.stacklite_clone.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionsRepo extends JpaRepository<Question, Integer> {
    Optional<Question> findByUser(User user );
    Optional<Question> findByAnswers(Answer answer);
    List<Question> findByQuestionContaining(String searchText);
}
