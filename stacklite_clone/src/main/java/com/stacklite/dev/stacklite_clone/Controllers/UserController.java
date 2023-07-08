package com.stacklite.dev.stacklite_clone.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stacklite.dev.stacklite_clone.Model.User;
import com.stacklite.dev.stacklite_clone.Services.UserService;

//import com.stacklite.dev.stacklite_clone.Repositories.UserRepository;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/allusers")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<List<User>>(userService.allUsers(), HttpStatus.OK);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable Integer Id) {
        return new ResponseEntity<Optional<User>>(userService.getUser(Id), HttpStatus.OK);
    }

}
