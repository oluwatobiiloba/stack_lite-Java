package com.stacklite.dev.stacklite_clone.Handlers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.stacklite.dev.stacklite_clone.Layers.Response.ErrorResponse;
import com.stacklite.dev.stacklite_clone.Utils.ResponseUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;

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
        } else if (ex instanceof ValidationException) {
            status = HttpStatus.BAD_REQUEST;
            errorMessage = ex.getMessage();
        } else if (ex instanceof DataIntegrityViolationException) {
            status = HttpStatus.CONFLICT;
            errorMessage = "Contect Error: Duplicate entry violation. The provided data conflicts with existing records. /n "
                    + ex.getMessage();
        }

        ErrorResponse<Object> errorResponse = responseUtil.createErrorResponse(errorMessage, status,
                requestPath);
        return ResponseEntity.status(status).body(errorResponse);
    }
}
