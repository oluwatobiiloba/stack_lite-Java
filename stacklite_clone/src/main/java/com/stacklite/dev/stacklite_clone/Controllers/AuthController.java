package com.stacklite.dev.stacklite_clone.Controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stacklite.dev.stacklite_clone.Dto.UserAuthDto;
import com.stacklite.dev.stacklite_clone.Dto.UserRegistrationDto;
import com.stacklite.dev.stacklite_clone.Layers.Response.Response;
import com.stacklite.dev.stacklite_clone.Model.User;
import com.stacklite.dev.stacklite_clone.Services.AuthService;
import com.stacklite.dev.stacklite_clone.Utils.JwtTokenUtil;
import com.stacklite.dev.stacklite_clone.Utils.ResponseUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authservice;

    @Autowired
    private ResponseUtil responseUtil;

    @Autowired
    private JwtTokenUtil jwtUtil;

    String token;

    @PostMapping("/login")
    public ResponseEntity<Response<Map<String, Object>>> authenticateEmailPassword(
            @Valid @RequestBody UserAuthDto userAuthDto)
            throws Exception {
        Optional<User> user = authservice.authenticate(userAuthDto);

        if (user.isPresent()) {
            token = jwtUtil.generateToken(user);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", user);
        return ResponseEntity.ok(responseUtil.createResponse(data, HttpStatus.OK, "/login"));
    }

    @PostMapping("/register")
    public ResponseEntity<Response<Optional<User>>> registerUser(@Valid @RequestBody UserRegistrationDto userRegDto)
            throws Exception {
        Optional<User> user = authservice.register(userRegDto);
        return ResponseEntity.ok(responseUtil.createResponse(user, HttpStatus.OK, "/login"));
    }

}
