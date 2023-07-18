package com.stacklite.dev.stacklite_clone.layers.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {

    private LocalDateTime timestamp;
    private HttpStatus status;
    private String message;
    private String path;
    private T data;
    private T extraArgs;

}
