package com.stacklite.dev.stacklite_clone.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stacklite.dev.stacklite_clone.layers.response.ErrorResponse;
import com.stacklite.dev.stacklite_clone.utils.DateConverter;
import com.stacklite.dev.stacklite_clone.utils.ResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    private final ResponseUtil responseUtil;

    @Autowired
    private DateConverter dateConverter;

    public JwtAuthenticationEntryPoint(ResponseUtil responseUtil) {
        this.responseUtil = responseUtil;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        LocalDateTime currentDateTime = LocalDateTime.now();
        String date = dateConverter.convertToDate(currentDateTime);

        ErrorResponse<Object> errorResponse = responseUtil.createAuthErrorResponse(
                authException.getMessage(), HttpStatus.UNAUTHORIZED, request.getServletPath(), date);

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), errorResponse);

    }
}