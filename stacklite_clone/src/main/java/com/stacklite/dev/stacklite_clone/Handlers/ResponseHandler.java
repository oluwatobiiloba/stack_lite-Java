package com.stacklite.dev.stacklite_clone.handlers;


import com.stacklite.dev.stacklite_clone.layers.response.ErrorResponse;
import com.stacklite.dev.stacklite_clone.layers.response.Response;
import com.stacklite.dev.stacklite_clone.utils.ResponseUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
public class ResponseHandler {
    private final ResponseUtil responseUtil;

    public ResponseHandler(ResponseUtil responseUtil) {
        this.responseUtil = responseUtil;
    }

    @ResponseBody
    public <T> ResponseEntity<String> sendResponse(T data, HttpStatus status, String path, T extras, String message) {
        Response<Object> response = responseUtil.createResponse(data, status, path, extras, message);
        String jsonResponse = responseUtil.toJson(response);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(jsonResponse, headers, status);
    }

    @ResponseBody
    public <T> ResponseEntity<String> sendErrorResponse(T data, HttpStatus status, String path, T extras, String message) {
        ErrorResponse<Object> response = responseUtil.createErrorResponse(message, status, path);
        String jsonResponse = responseUtil.toJson(response);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(jsonResponse, headers, status);
    }
}
