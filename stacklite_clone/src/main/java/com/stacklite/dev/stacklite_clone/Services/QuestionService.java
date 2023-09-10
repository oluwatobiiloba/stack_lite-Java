package com.stacklite.dev.stacklite_clone.Services;

import com.stacklite.dev.stacklite_clone.Mapper.QuestionMapper;
import com.stacklite.dev.stacklite_clone.Model.Question;
import com.stacklite.dev.stacklite_clone.Repositories.QuestionsRepo;
import com.stacklite.dev.stacklite_clone.dto.questions.QuestionRespDto;
import com.stacklite.dev.stacklite_clone.utils.Pagination;
import com.stacklite.dev.stacklite_clone.utils.SearchResultBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
}
