package com.stacklite.dev.stacklite_clone.layers.response;

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
    private String timestamp;
    private HttpStatus status;
    private String message;
    private String path;

}
