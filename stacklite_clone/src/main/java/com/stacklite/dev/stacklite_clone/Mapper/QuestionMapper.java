package com.stacklite.dev.stacklite_clone.Mapper;

import com.stacklite.dev.stacklite_clone.Model.Question;
import com.stacklite.dev.stacklite_clone.Model.User;
import com.stacklite.dev.stacklite_clone.dto.questions.QuestionRespDto;

import java.util.HashMap;
import java.util.Map;

public class QuestionMapper {
    public static QuestionRespDto mapToQuestionRespDto(Question question) {

        try {
            return new QuestionRespDto(
                    question.getId(),
                    question.getUuid(),
                    mapUserToBasicInfo(question.getUser()),
                    question.getQuestion(),
                    question.getStatus(),
                    question.getCreatedAt(),
                    question.getAnswers());
        }catch (Exception e){
            return null;
        }

    }

    public static Map<String,String> mapUserToBasicInfo(User user){
        Map<String,String> userMap = new HashMap<>();
        userMap.put("id", String.valueOf(user.getId()));
        userMap.put("uuid",user.getUuid());
        userMap.put("username",user.getUsername());
        userMap.put("email", user.getEmail());
        return userMap;
    }

}
