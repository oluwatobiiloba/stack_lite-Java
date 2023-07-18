package com.stacklite.dev.stacklite_clone.handlers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.stacklite.dev.stacklite_clone.layers.response.ErrorResponse;
import com.stacklite.dev.stacklite_clone.utils.ResponseUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ResponseUtil responseUtil;

    public GlobalExceptionHandler(ResponseUtil responseUtil) {
        this.responseUtil = responseUtil;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse<Object>> handleException(HttpServletRequest request, Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String errorMessage = "Internal server error";
        String requestPath = request.getRequestURI();

        if (ex instanceof NotFoundException) {
            status = HttpStatus.NOT_FOUND;
            errorMessage = ex.getMessage();
        } else if (ex instanceof ValidationException) {
            status = HttpStatus.BAD_REQUEST;
            errorMessage = ex.getMessage();
        } else if (ex instanceof UnauthorizedException) {
            status = HttpStatus.UNAUTHORIZED;
            errorMessage = ex.getMessage();
        } else if (ex instanceof DataIntegrityViolationException) {
            status = HttpStatus.CONFLICT;
            errorMessage = "Contect Error: Duplicate entry violation. The provided data conflicts with existing records. /n "
                    + ex.getMessage();
        } else if (ex instanceof MethodArgumentNotValidException) {
            status = HttpStatus.BAD_REQUEST;
            FieldError err;
            err = ((MethodArgumentNotValidException) ex).getFieldError();
            errorMessage = "Invalid input - " + Objects.requireNonNull(err).getDefaultMessage() + ". You inputed - " + err.getRejectedValue();
        } else if (ex instanceof NoHandlerFoundException) {
            status = HttpStatus.NOT_FOUND;
            errorMessage = ex.getMessage();
        }

        ErrorResponse<Object> errorResponse = responseUtil.createErrorResponse(errorMessage, status,
                requestPath);
        return ResponseEntity.status(status).body(errorResponse);
    }
}
