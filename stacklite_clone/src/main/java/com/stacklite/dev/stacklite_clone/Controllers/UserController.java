package com.stacklite.dev.stacklite_clone.controllers;

import com.stacklite.dev.stacklite_clone.dto.user.UserProfileUpdateDto;
import com.stacklite.dev.stacklite_clone.dto.user.UserRespDto;
import com.stacklite.dev.stacklite_clone.handlers.NotFoundException;
import com.stacklite.dev.stacklite_clone.handlers.ResponseHandler;
import com.stacklite.dev.stacklite_clone.layers.response.Response;
import com.stacklite.dev.stacklite_clone.Model.User;
import com.stacklite.dev.stacklite_clone.Services.UserDetailsImpl;
import com.stacklite.dev.stacklite_clone.Services.UserService;
import com.stacklite.dev.stacklite_clone.utils.EntityMapper;
import jakarta.validation.Valid;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    private final ResponseHandler responseHandler;

    public UserController(UserService userService, ResponseHandler responseHandler) {
        this.userService = userService;
        this.responseHandler = responseHandler;

    }

    @GetMapping("/allusers")
    public ResponseEntity<Response<Object>> allUsers(
            @RequestParam(required = false) Map<String, String> queryParameters) {
        Map<String, Object> users = userService.allUsers(queryParameters);
        if (users.isEmpty()) {
            throw new NotFoundException("No user(s) found");
        }

                Map<String, Link> hateoasLink = EntityMapper.createLink(
                "search-users",
                this.getClass() ,
                "search",
                queryParameters);

        return responseHandler.sendResponse(users, HttpStatus.OK, null,hateoasLink);
    }

    @GetMapping("/search")
    public ResponseEntity<Response<Object>> searchUsers(
            @RequestParam(required = false) Map<String, String> queryParameters) {
        Map<String, Object> users = userService.searchUsers(queryParameters);
        if (users.isEmpty()) {
            throw new NotFoundException("No user(s) found");
        }


        return responseHandler.sendResponse(users, HttpStatus.OK, null,null);
    }

    @GetMapping("/{Id}/profile")
    public ResponseEntity<Response<Object>> getUser(@PathVariable Integer Id) {
        Optional<UserRespDto> user = userService.getUser(Id);

        Map<String, Link> hateoasLink = EntityMapper.createLink(
                "search-users",
                this.getClass() ,
                "search",
                null);
        return responseHandler.sendResponse(user, HttpStatus.OK, null,hateoasLink);
    }

    @PutMapping("/profile/edit")
    public ResponseEntity<Response<Object>> updateUser(@Valid @RequestBody UserProfileUpdateDto userUpdateDTO) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userdetails = (UserDetailsImpl) auth.getPrincipal();
        Optional<User> user = userService.updateProfile(userdetails.getId(), userUpdateDTO);

        Map<String, Link> hateoasLink = EntityMapper.createLink(
                "get-user",
                this.getClass() ,
                String.format("/%s/profile/edit",userdetails.getId()),
                null);

        return responseHandler.sendResponse(user, HttpStatus.OK, null,hateoasLink);

    }

}
