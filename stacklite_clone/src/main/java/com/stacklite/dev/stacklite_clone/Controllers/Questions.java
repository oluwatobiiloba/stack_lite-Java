package com.stacklite.dev.stacklite_clone.controllers;

import com.stacklite.dev.stacklite_clone.Services.QuestionService;
import com.stacklite.dev.stacklite_clone.Services.UserDetailsImpl;
import com.stacklite.dev.stacklite_clone.dto.questions.QuestionCreationDto;
import com.stacklite.dev.stacklite_clone.dto.questions.QuestionRespDto;
import com.stacklite.dev.stacklite_clone.handlers.NotFoundException;
import com.stacklite.dev.stacklite_clone.handlers.ResponseHandler;
import com.stacklite.dev.stacklite_clone.utils.EntityMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/questions")
public class Questions {
    private final QuestionService questionService;
    private final ResponseHandler responseHandler;

    @Autowired
    public Questions(QuestionService questionService, ResponseHandler responseHandler) {
        this.questionService = questionService;
        this.responseHandler = responseHandler;
    }

    @Secured("ROLE_USER")
    @GetMapping
    public ResponseEntity<String> getAllQuestions(@RequestParam(required = false) Map<String, String> queryParameters) {
        Map<String, Object> questions = questionService.getQuestions(queryParameters);
        if (Objects.isNull(questions) || questions.isEmpty()) {
            throw new NotFoundException("No question(s) found");
        }
        Map<String, Link> hateoasLink = EntityMapper.createLink(
                "search-questions",
                this.getClass(),
                "search",
                queryParameters);
        return responseHandler.sendResponse(questions, HttpStatus.FOUND, null, hateoasLink, null);
    }

    @Secured("ROLE_USER")
    @PostMapping
    public ResponseEntity<String> createQuestion(@Valid @RequestBody QuestionCreationDto questionCreationDto) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        QuestionRespDto question = questionService.createQuestion(userDetails.getId(), questionCreationDto);
        Map<String, Link> hateoasLink = EntityMapper.createLink(
                "get-question",
                this.getClass(),
                String.format("/%s", question.id()),
                null);
        return responseHandler.sendResponse(question, HttpStatus.CREATED, null, hateoasLink, null);
    }

    @Secured("ROLE_USER")
    @GetMapping("/{id}")
    public ResponseEntity<String> getQuestion(@PathVariable Integer id) {
        Optional<QuestionRespDto> question = questionService.getQuestion(id);
        Map<String, Link> hateoasLink = EntityMapper.createLink(
                "edit-question",
                this.getClass(),
                String.format("/%s/edit", question.map(QuestionRespDto::id).orElse(null)),
                null);

        return responseHandler.sendResponse(question, HttpStatus.FOUND, null, hateoasLink, null);

    }
}
