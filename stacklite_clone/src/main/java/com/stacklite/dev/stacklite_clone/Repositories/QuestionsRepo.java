package com.stacklite.dev.stacklite_clone.Repositories;

import com.stacklite.dev.stacklite_clone.Model.Answer;
import com.stacklite.dev.stacklite_clone.Model.Question;
import com.stacklite.dev.stacklite_clone.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionsRepo extends JpaRepository<Question, Integer> {
    Optional<Question> findByUser(User user );
    Optional<Question> findByAnswers(Answer answer);
    List<Question> findByQuestionContaining(String searchText);
    @Query("SELECT q FROM Question q WHERE " +
            "(:question IS NULL OR q.question LIKE %:question%) AND " +
            "(:status IS NULL OR q.status =:status ) AND " +
            "(:userId IS NULL OR q.user.id =:userId) ")
    Page<Question> findByParams(
            @Param("question") String question,
            @Param("status") Integer status,
            @Param("userId") Integer userId,
            Pageable pageable
    );

}
