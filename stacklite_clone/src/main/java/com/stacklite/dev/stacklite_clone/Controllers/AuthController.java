package com.stacklite.dev.stacklite_clone.Controllers;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stacklite.dev.stacklite_clone.Dto.UserAuthDto;
import com.stacklite.dev.stacklite_clone.Dto.UserRegistrationDto;
import com.stacklite.dev.stacklite_clone.Handlers.NotFoundException;
import com.stacklite.dev.stacklite_clone.Handlers.UnauthorizedException;
import com.stacklite.dev.stacklite_clone.Model.User;
import com.stacklite.dev.stacklite_clone.Services.AuthService;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authservice;

    @PostMapping("/login")
    public ResponseEntity<Optional<User>> authenticateEmailPassword(@Valid @RequestBody UserAuthDto userAuthDto)
            throws Exception {
        return new ResponseEntity<Optional<User>>(authservice.authenticate(userAuthDto), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Optional<User>> registerUser(@Valid @RequestBody UserRegistrationDto userRegDto)
            throws Exception {
        return new ResponseEntity<Optional<User>>(authservice.register(userRegDto), HttpStatus.CREATED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {

        System.out.println(ex.getClass());

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String errorMessage = "Internal server error";

        if (ex instanceof UnauthorizedException) {
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

        return ResponseEntity.status(status).body(errorMessage);
    }

}
