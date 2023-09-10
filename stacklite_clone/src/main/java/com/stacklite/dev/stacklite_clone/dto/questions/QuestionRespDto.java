package com.stacklite.dev.stacklite_clone.dto.questions;

import com.stacklite.dev.stacklite_clone.Model.Answer;

import java.sql.Timestamp;
import java.util.List;

public record QuestionRespDto(

        Integer id,
        String uuid,
        java.util.Map<String, String> user,
        String question,
        Integer status,
        Timestamp createdAt,

        List<Answer> answers


) {

}
