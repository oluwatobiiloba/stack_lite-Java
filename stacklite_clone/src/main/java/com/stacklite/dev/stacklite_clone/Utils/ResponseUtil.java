package com.stacklite.dev.stacklite_clone.Utils;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.stacklite.dev.stacklite_clone.Layers.Response.ErrorResponse;
import com.stacklite.dev.stacklite_clone.Layers.Response.Response;

@Component
public class ResponseUtil {
    public <T> Response<T> createResponse(T data, HttpStatus status) {
        Response<T> response = new Response<>();
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(status);
        response.setData(data);
        return response;
    }

    public <T> ErrorResponse<T> createErrorResponse(String errorMessage, HttpStatus status) {
        ErrorResponse<T> errorResponse = new ErrorResponse<>();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatus(status);
        errorResponse.setMessage(errorMessage);
        return errorResponse;
    }
}
