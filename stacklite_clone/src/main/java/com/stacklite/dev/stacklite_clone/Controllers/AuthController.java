package com.stacklite.dev.stacklite_clone.Controllers;

import java.util.Optional;

import com.stacklite.dev.stacklite_clone.Dto.User.*;
import com.stacklite.dev.stacklite_clone.Handlers.ResponseHandler;
import com.stacklite.dev.stacklite_clone.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stacklite.dev.stacklite_clone.Layers.Response.Response;
import com.stacklite.dev.stacklite_clone.Model.User;
import com.stacklite.dev.stacklite_clone.Services.AuthService;
import com.stacklite.dev.stacklite_clone.Utils.JwtTokenUtil;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authservice;

    @Autowired
    private JwtTokenUtil jwtUtil;

    @Autowired
    private ResponseHandler responseHandler;

    String token;

    @PostMapping("/login")
    public ResponseEntity<Response<Object>> authenticateEmailPassword(
            @Valid @RequestBody UserAuthDto userAuthDto)
            throws Exception {
        Optional<User> user = authservice.authenticate(userAuthDto);

        if (user.isPresent()) {
            token = jwtUtil.generateToken(user);
        }
        UserAuthorizationDto data = new UserAuthorizationDto(user, token);
        return responseHandler.sendResponse(data, HttpStatus.OK, "/login");
    }

    @PostMapping("/register")
    public ResponseEntity<Response<Object>> registerUser(@Valid @RequestBody UserRegistrationDto userRegDto)
            throws Exception {
        Optional<User> user = authservice.register(userRegDto);
        if (user.isPresent()) {
            token = jwtUtil.generateToken(user);
        }

        UserRegRespDto data = new UserRegRespDto(user.map(UserMapper::mapToUserRespDto), token);
        return responseHandler.sendResponse(data, HttpStatus.OK, "/register");
    }

}
