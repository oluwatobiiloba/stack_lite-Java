package com.stacklite.dev.stacklite_clone.Services;

import com.stacklite.dev.stacklite_clone.Mapper.QuestionMapper;
import com.stacklite.dev.stacklite_clone.Model.Question;
import com.stacklite.dev.stacklite_clone.Repositories.QuestionsRepo;
import com.stacklite.dev.stacklite_clone.dto.questions.QuestionCreationDto;
import com.stacklite.dev.stacklite_clone.dto.questions.QuestionRespDto;
import com.stacklite.dev.stacklite_clone.utils.Pagination;
import com.stacklite.dev.stacklite_clone.utils.SearchResultBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.stacklite.dev.stacklite_clone.Mapper.QuestionMapper.mapToQuestionRespDto;

@Service
public class QuestionService {
    private final QuestionsRepo questionsRepo;

    public QuestionService(QuestionsRepo questionsRepo){
        this.questionsRepo = questionsRepo;
    }

    public Map<String,Object> getQuestions(Map<String, String> queryParameters){

        Pageable pageable = Pagination.createPageable(queryParameters);
        if(queryParameters.containsKey("question") || queryParameters.containsKey("status") || queryParameters.containsKey("userId")) {

            String questionParam = queryParameters.get("question");
            Integer statusParam = queryParameters.containsKey("status") ? Integer.parseInt(queryParameters.get("status")) : null;
            Integer userIdParam = queryParameters.containsKey("userId") ? Integer.parseInt(queryParameters.get("userId")) : null;

            Page<Question> questionPage = questionsRepo.findByParams(
                    questionParam,
                    statusParam,
                    userIdParam,
                    pageable);

            List<QuestionRespDto> questions = questionPage
                    .getContent()
                    .stream()
                    .map(QuestionMapper::mapToQuestionRespDto)
                    .toList();

            return SearchResultBuilder.buildResult(questions, questionPage);
        } else {
            Page<Question> questionPage = questionsRepo.findAll(pageable);
            List<QuestionRespDto> questions = questionPage
                    .getContent()
                    .stream()
                    .map(QuestionMapper::mapToQuestionRespDto)
                    .toList();
            return SearchResultBuilder.buildResult(questions,questionPage);
        }
    }

    public QuestionRespDto createQuestion(Integer userId,QuestionCreationDto questionCreationDto){
        Question question = new Question();
        question.setQuestion(questionCreationDto.getQuestion());
        question.setUserId(userId);
        Question createdQuestion = questionsRepo.save(question);
        return mapToQuestionRespDto(createdQuestion);
    }

    public Optional<QuestionRespDto> getQuestion(Integer id){
            return  questionsRepo.findById(id).map(QuestionMapper::mapToQuestionRespDto);
    }
}
