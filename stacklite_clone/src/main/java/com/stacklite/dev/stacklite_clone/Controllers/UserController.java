package com.stacklite.dev.stacklite_clone.Controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stacklite.dev.stacklite_clone.Model.User;
import com.stacklite.dev.stacklite_clone.Services.UserService;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.websocket.server.PathParam;

import com.stacklite.dev.stacklite_clone.Dto.UserProfileUpdateDto;
import com.stacklite.dev.stacklite_clone.Handlers.NotFoundException;
//import com.stacklite.dev.stacklite_clone.Repositories.UserRepository;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/allusers")
    public ResponseEntity<Optional<List<User>>> getAllUsers(
            @RequestParam(required = false) Map<String, String> queryParameters) {
        return new ResponseEntity<Optional<List<User>>>(userService.allUsers(queryParameters), HttpStatus.OK);
    }

    @GetMapping("/{Id}/profile")
    public ResponseEntity<Optional<User>> getUser(@PathVariable Integer Id) {
        return new ResponseEntity<Optional<User>>(userService.getUser(Id), HttpStatus.OK);
    }

    @PutMapping("/{Id}/profile/edit")
    public ResponseEntity<Optional<User>> updateUser(@PathVariable Integer Id,
            @Valid @RequestBody UserProfileUpdateDto userUpdateDTO) throws Exception {
        return new ResponseEntity<Optional<User>>(userService.updateProfile(Id, userUpdateDTO), HttpStatus.OK);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {

        System.out.println(ex.getClass());

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String errorMessage = "Internal server error";

        if (ex instanceof NotFoundException) {
            status = HttpStatus.NOT_FOUND;
            errorMessage = ex.getMessage();
        } else if (ex instanceof ValidationException) {
            status = HttpStatus.BAD_REQUEST;
            errorMessage = ex.getMessage();
        }

        return ResponseEntity.status(status).body(errorMessage);
    }

}
