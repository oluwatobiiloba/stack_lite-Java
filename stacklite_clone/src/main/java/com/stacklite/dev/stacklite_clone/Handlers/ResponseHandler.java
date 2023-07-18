package com.stacklite.dev.stacklite_clone.handlers;


import com.stacklite.dev.stacklite_clone.layers.response.Response;
import com.stacklite.dev.stacklite_clone.utils.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseHandler {
    private final ResponseUtil responseUtil;

    public ResponseHandler(ResponseUtil responseUtil) {
        this.responseUtil = responseUtil;
    }

    public <T> ResponseEntity<Response<Object>> sendResponse(T data, HttpStatus status, String path, T extras) {
        Response<Object> response = responseUtil.createResponse(data, status, path, extras);
        return ResponseEntity.status(status).body(response);
    }
}
