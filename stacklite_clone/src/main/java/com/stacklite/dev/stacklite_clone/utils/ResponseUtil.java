package com.stacklite.dev.stacklite_clone.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stacklite.dev.stacklite_clone.layers.response.ErrorResponse;
import com.stacklite.dev.stacklite_clone.layers.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ResponseUtil {

    private final ObjectMapper objectMapper;

    public ResponseUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    public <T> Response<T> createResponse(T data, HttpStatus status, String path, T extraArgs, String message) {
        Response<T> response = new Response<>();
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(status);
        response.setData(data);
        response.setPath(path);
        response.setExtraArgs(extraArgs);
        response.setMessage(message);
        return response;
    }

    public <T> ErrorResponse<T> createErrorResponse(String errorMessage, HttpStatus status, String path) {
        ErrorResponse<T> errorResponse = new ErrorResponse<>();
        errorResponse.setTimestamp(LocalDateTime.now().toString());
        errorResponse.setStatus(status);
        errorResponse.setMessage(errorMessage);
        errorResponse.setPath(path);
        errorResponse.setPath(path);
        return errorResponse;
    }

    public <T> ErrorResponse<T> createAuthErrorResponse(String errorMessage, HttpStatus status, String path,
            String date) {
        ErrorResponse<T> errorResponse = new ErrorResponse<>();
        errorResponse.setTimestamp(date);
        errorResponse.setStatus(status);
        errorResponse.setMessage(errorMessage);
        errorResponse.setPath(path);
        return errorResponse;
    }

    public String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(),e);
        }
    }
}
