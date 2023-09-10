package com.stacklite.dev.stacklite_clone.dto.questions;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionCreationDto {

    @NotBlank(message = "Question is required")
    private String question;
    
}
