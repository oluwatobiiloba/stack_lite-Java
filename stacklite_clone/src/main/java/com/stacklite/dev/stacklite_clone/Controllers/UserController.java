package com.stacklite.dev.stacklite_clone.Controllers;

import java.util.List;
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

import com.stacklite.dev.stacklite_clone.Model.User;
import com.stacklite.dev.stacklite_clone.Services.UserService;
import jakarta.validation.Valid;
import com.stacklite.dev.stacklite_clone.Dto.UserProfileUpdateDto;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/admin/allusers")
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

}
