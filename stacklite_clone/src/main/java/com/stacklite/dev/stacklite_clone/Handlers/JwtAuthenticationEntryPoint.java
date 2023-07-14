package com.stacklite.dev.stacklite_clone.Handlers;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stacklite.dev.stacklite_clone.Layers.Response.ErrorResponse;
import com.stacklite.dev.stacklite_clone.Utils.DateConverter;
import com.stacklite.dev.stacklite_clone.Utils.ResponseUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
        // final Map<String, Object> body = new HashMap<>();
        // body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        // body.put("error", "Unauthorized");
        // body.put("message", authException.getMessage());
        // body.put("path", request.getServletPath());

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