package com.stacklite.dev.stacklite_clone.Layers.Response;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse<T> {
    private LocalDateTime timestamp;
    private HttpStatus status;
    private String message;
}
