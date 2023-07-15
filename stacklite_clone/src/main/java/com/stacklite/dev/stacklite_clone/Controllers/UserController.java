package com.stacklite.dev.stacklite_clone.Controllers;

import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stacklite.dev.stacklite_clone.Handlers.NotFoundException;
import com.stacklite.dev.stacklite_clone.Handlers.ResponseHandler;
import com.stacklite.dev.stacklite_clone.Layers.Response.Response;
import com.stacklite.dev.stacklite_clone.Model.User;
import com.stacklite.dev.stacklite_clone.Services.UserService;
import jakarta.validation.Valid;
import com.stacklite.dev.stacklite_clone.Dto.UserProfileUpdateDto;
import com.stacklite.dev.stacklite_clone.Dto.UserRespDto;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ResponseHandler responseHandler;

    @GetMapping("/allusers")
    public ResponseEntity<Response<Object>> getAllUsers(
            @RequestParam(required = false) Map<String, String> queryParameters) {
        // return new
        // ResponseEntity<Optional<List<UserRespDto>>>(userService.allUsers(queryParameters),
        // HttpStatus.OK);
        Map<String, Object> users = userService.allUsers(queryParameters);
        if (users.isEmpty()) {
            throw new NotFoundException("No user(s) found");
        }

        return responseHandler.sendResponse(users, HttpStatus.OK, null);
    }

    @GetMapping("/search")
    public ResponseEntity<Response<Object>> searchUsers(
            @RequestParam(required = false) Map<String, String> queryParameters) {
        Map<String, Object> users = userService.searchUsers(queryParameters);
        if (users.isEmpty()) {
            throw new NotFoundException("No user(s) found");
        }
        return responseHandler.sendResponse(users, HttpStatus.OK, null);
    }

    @GetMapping("/{Id}/profile")
    public ResponseEntity<Optional<UserRespDto>> getUser(@PathVariable Integer Id) {
        return new ResponseEntity<Optional<UserRespDto>>(userService.getUser(Id), HttpStatus.OK);
    }

    @PutMapping("/{Id}/profile/edit")
    public ResponseEntity<Optional<User>> updateUser(@PathVariable Integer Id,
            @Valid @RequestBody UserProfileUpdateDto userUpdateDTO) throws Exception {
        return new ResponseEntity<Optional<User>>(userService.updateProfile(Id, userUpdateDTO), HttpStatus.OK);

    }

}
