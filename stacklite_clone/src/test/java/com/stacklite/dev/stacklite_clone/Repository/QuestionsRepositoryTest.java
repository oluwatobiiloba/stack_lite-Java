package com.stacklite.dev.stacklite_clone.Repository;


import com.stacklite.dev.stacklite_clone.Model.Answer;
import com.stacklite.dev.stacklite_clone.Model.Question;
import com.stacklite.dev.stacklite_clone.Model.User;
import com.stacklite.dev.stacklite_clone.Repositories.AnswersRepo;
import com.stacklite.dev.stacklite_clone.Repositories.QuestionsRepo;
import com.stacklite.dev.stacklite_clone.Repositories.UsersRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(true)
public class QuestionsRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private QuestionsRepo questionRepo;

    @Autowired
    private UsersRepo userRepo;

    @Autowired
    private AnswersRepo answersRepo;

    @Test
    public void Create_Question_Test (){
        User user = createUser1();
        User savedUser = userRepo.save(user);
        Question question = newQuestion1(user);
        Question savedQuestion = questionRepo.save(question);
        Question existQuestion = entityManager.find(Question.class, savedQuestion.getId());
        assertThat(existQuestion.getQuestion()).isEqualTo(question.getQuestion());
        assertThat(existQuestion.getUser()).isEqualTo(savedUser);
    };

    @Test
    public void Update_Question_Test () {
        User user = createUser1();
        User savedUser = userRepo.save(user);
        Question question = newQuestion1(user);
        Question savedQuestion = questionRepo.save(question);
        assertThat(savedQuestion.getQuestion()).isEqualTo(question.getQuestion());
        savedQuestion.setQuestion("Updated Question");
        questionRepo.save(savedQuestion);
        assertThat(questionRepo.findById(savedQuestion.getId()).get().getQuestion()).isEqualTo("Updated Question");
    }

    @Test
    public void Delete_Question_Test () {
        User user = createUser1();
        userRepo.save(user);
        Question question = newQuestion1(user);
        Question savedQuestion = questionRepo.save(question);
        questionRepo.deleteById(savedQuestion.getId());
        assertThat(questionRepo.findById(savedQuestion.getId())).isEmpty();
    }

    @Test
    public void Get_All_Question_Test () {
            List<Question> questions = questionRepo.findAll();
            assertThat(questions).isNotEmpty();
    }


    @Test
    public void Get_Question_By_User_Test () {
        User user = createUser1();
        userRepo.save(user);
        Question question = newQuestion1(user);
        questionRepo.save(question);
        assertThat(questionRepo.findByUser(user)).isNotEmpty();
        assertThat(questionRepo.findByUser(user).get().toString()).isEqualTo(question.toString());

    }

    @Test
    public void Get_Question_By_Answer_Test () {
        User user = createUser1();
        userRepo.save(user);
        Question question = newQuestion1(user);
        Question savedQuestion = questionRepo.save(question);
        Answer answer = newAnswer(question,user);
        Answer savedAnswer = answersRepo.save(answer);
        assertThat(questionRepo.findByAnswers(savedAnswer)).isNotEmpty();
        assertThat(questionRepo.findByAnswers(savedAnswer).get().getId()).isEqualTo(savedQuestion.getId());
    }

    @Test
    public void Get_Question_By_Text_Test () {
        User user = createUser1();
        userRepo.save(user);
        Question question = newQuestion1(user);
        Question savedQuestion = questionRepo.save(question);

        List<Question> questio2 = questionRepo.findByQuestionContaining("Test q");
       assertThat(questionRepo.findByQuestionContaining("Test")).isNotEmpty();
    }



    private User createUser1(){
        return User.builder()
                .email("test523@gmail.com")
                .password("test202020")
                .firstName("Oluwatobiloba2")
                .lastName("Aremu2")
                .stack("WebDesign")
                .username("testingJava2")
                .isVerified(false)
                .phoneNumber((long) 810223466)
                .build();
    }
    private Question newQuestion1(User user){
        return Question.builder()
                .question("Test Question")
                .status(1)
                .user(user)
                .build();

    };

    private Answer newAnswer(Question question, User user){
        return Answer.builder()
                .answer("Test Answer")
                .question(question)
                .user(user)
                .build();

    };

    private Question newQuestion2(User user){
        return Question.builder()
                .question("Test Question 2")
                .status(1)
                .user(user)
                .build();

    };

}
