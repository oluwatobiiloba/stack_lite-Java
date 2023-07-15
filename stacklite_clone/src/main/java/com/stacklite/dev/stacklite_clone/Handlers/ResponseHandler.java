package com.stacklite.dev.stacklite_clone.Handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.stacklite.dev.stacklite_clone.Layers.Response.Response;
import com.stacklite.dev.stacklite_clone.Utils.ResponseUtil;

@Component
public class ResponseHandler {
    @Autowired
    private ResponseUtil responseUtil;

    public <T> ResponseEntity<Response<Object>> sendResponse(T data, HttpStatus status, String path) {
        Response<Object> response = responseUtil.createResponse(data, status, path);
        return ResponseEntity.status(status).body(response);
    }
}
