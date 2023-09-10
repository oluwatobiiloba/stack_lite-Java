package com.stacklite.dev.stacklite_clone.controllers;

import com.stacklite.dev.stacklite_clone.Services.QuestionService;
import com.stacklite.dev.stacklite_clone.handlers.NotFoundException;
import com.stacklite.dev.stacklite_clone.handlers.ResponseHandler;
import com.stacklite.dev.stacklite_clone.utils.EntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

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
    public ResponseEntity<String> getQuestions(@RequestParam(required = false) Map<String, String> queryParameters){

        Map<String, Object> questions = questionService.getQuestions(queryParameters);

        if(Objects.isNull(questions) || questions.isEmpty()){
            throw  new NotFoundException("No question(s) found");
        }

        Map<String, Link> hateoasLink = EntityMapper.createLink(
                "search-questions",
                this.getClass(),
                "search",
                queryParameters
        );
        return responseHandler.sendResponse(questions, HttpStatus.OK, null,hateoasLink ,null);
    }
}
