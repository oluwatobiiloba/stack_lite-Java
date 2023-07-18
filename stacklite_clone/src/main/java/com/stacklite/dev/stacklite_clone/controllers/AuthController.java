package com.stacklite.dev.stacklite_clone.controllers;

import com.stacklite.dev.stacklite_clone.dto.user.UserAuthDto;
import com.stacklite.dev.stacklite_clone.dto.user.UserAuthorizationDto;
import com.stacklite.dev.stacklite_clone.dto.user.UserRegRespDto;
import com.stacklite.dev.stacklite_clone.dto.user.UserRegistrationDto;
import com.stacklite.dev.stacklite_clone.handlers.ResponseHandler;
import com.stacklite.dev.stacklite_clone.layers.response.Response;
import com.stacklite.dev.stacklite_clone.Mapper.UserMapper;
import com.stacklite.dev.stacklite_clone.Model.User;
import com.stacklite.dev.stacklite_clone.Services.AuthService;
import com.stacklite.dev.stacklite_clone.utils.JwtTokenUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authservice;

    private final JwtTokenUtil jwtUtil;

    private final ResponseHandler responseHandler;

    String token;

    @Autowired
    public AuthController(AuthService authservice, JwtTokenUtil jwtUtil, ResponseHandler responseHandler) {
        this.authservice = authservice;
        this.jwtUtil = jwtUtil;
        this.responseHandler = responseHandler;
    }

    @PostMapping("/login")
    public ResponseEntity<Response<Object>> authenticateEmailPassword(
            @Valid @RequestBody UserAuthDto userAuthDto){
        Optional<User> user = authservice.authenticate(userAuthDto);

        if (user.isPresent()) {
            token = jwtUtil.generateToken(user);
        }
        UserAuthorizationDto data = new UserAuthorizationDto(user, token);
        return responseHandler.sendResponse(data, HttpStatus.OK, "/login",null);
    }

    @PostMapping("/register")
    public ResponseEntity<Response<Object>> registerUser(@Valid @RequestBody UserRegistrationDto userRegDto){
        Optional<User> user = authservice.register(userRegDto);
        if (user.isPresent()) {
            token = jwtUtil.generateToken(user);
        }

        UserRegRespDto data = new UserRegRespDto(user.map(UserMapper::mapToUserRespDto), token);
        return responseHandler.sendResponse(data, HttpStatus.CREATED, "/register",null);
    }

}
