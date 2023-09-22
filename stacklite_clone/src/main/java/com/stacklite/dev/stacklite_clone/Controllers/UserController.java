package com.stacklite.dev.stacklite_clone.controllers;

import com.stacklite.dev.stacklite_clone.Model.User;
import com.stacklite.dev.stacklite_clone.Services.UserDetailsImpl;
import com.stacklite.dev.stacklite_clone.Services.UserService;
import com.stacklite.dev.stacklite_clone.dto.user.UserAuthorizationDto;
import com.stacklite.dev.stacklite_clone.dto.user.UserProfileUpdateDto;
import com.stacklite.dev.stacklite_clone.dto.user.UserRespDto;
import com.stacklite.dev.stacklite_clone.handlers.NotFoundException;
import com.stacklite.dev.stacklite_clone.handlers.ResponseHandler;
import com.stacklite.dev.stacklite_clone.utils.EntityMapper;
import jakarta.validation.Valid;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
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

    @Secured({ "ROLE_USER", "ROLE_ADMIN" })
    @GetMapping("/allusers")
    public ResponseEntity<String> allUsers(
            @RequestParam(required = false) Map<String, String> queryParameters) throws NotFoundException {
        Map<String, Object> users = userService.allUsers(queryParameters);
        if (users.isEmpty()) {
            throw new NotFoundException("No user(s) found");
        }
        Map<String, Link> hateoasLink = EntityMapper.createLink(
                "search-users",
                this.getClass(),
                "search",
                queryParameters);

        return responseHandler.sendResponse(users, HttpStatus.FOUND, null, hateoasLink, null);
    }

    @Secured({ "ROLE_USER", "ROLE_ADMIN" })
    @GetMapping("/search")
    public ResponseEntity<String> searchUsers(
            @RequestParam(required = true) Map<String, String> queryParameters) {
        Map<String, Object> users = userService.searchUsers(queryParameters);
        if (users.isEmpty()) {
            throw new NotFoundException("No user(s) found");
        }

        return responseHandler.sendResponse(users, HttpStatus.FOUND, null, null, null);
    }

    @Secured({ "ROLE_USER", "ROLE_ADMIN" })
    @GetMapping("/{Id}/profile")
    public ResponseEntity<String> getUser(@PathVariable Integer Id) {
        Optional<UserRespDto> user = userService.getUser(Id);

        Map<String, Link> hateoasLink = EntityMapper.createLink(
                "search-users",
                this.getClass(),
                "search",
                null);
        return responseHandler.sendResponse(user, HttpStatus.FOUND, null, hateoasLink, null);
    }

    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    @GetMapping("/checkEmailAvailability/{email}")
    public ResponseEntity<String> checkUserEmailAvailability(@PathVariable String email) {
        HashMap<String, Boolean> emailExistsObj = new HashMap<>();
        Boolean emailExists;
        emailExists = userService.checkEmailAvailability(email);
        emailExistsObj.put("emailExists", emailExists);
        return responseHandler.sendResponse(emailExistsObj, HttpStatus.OK,
                String.format("/checkEmailAvailability/%s", email), null, null);
    }

    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    @GetMapping("/checkUsername/{username}")
    public ResponseEntity<String> checkUsernameAvailability(@PathVariable String username) {
        HashMap<String, Boolean> usernameExistsObj = new HashMap<>();
        Boolean usernameExists;
        usernameExists = userService.checkUsernameAvailability(username);
        usernameExistsObj.put("usernameExists", usernameExists);
        return responseHandler.sendResponse(usernameExistsObj, HttpStatus.OK,
                String.format("/checkUsername/%s", username), null, null);
    }

    @PutMapping("/profile/edit")
    public ResponseEntity<String> updateUser(@Valid @RequestBody UserProfileUpdateDto userUpdateDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userdetails = (UserDetailsImpl) auth.getPrincipal();
        Optional<User> user = userService.updateProfile(userdetails.getId(), userUpdateDTO);

        Map<String, Link> hateoasLink = EntityMapper.createLink(
                "get-user",
                this.getClass(),
                String.format("/%s/profile/edit", userdetails.getId()),
                null);

        return responseHandler.sendResponse(user, HttpStatus.ACCEPTED, null, hateoasLink, null);

    }

    @Secured({ "ROLE_USER" })
    @PostMapping("/upload-profile-image")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userdetails = (UserDetailsImpl) auth.getPrincipal();

        Optional<User> user = userService.uploadProfileImage(multipartFile, userdetails.getId(),
                userdetails.getUsername());
        UserAuthorizationDto data = new UserAuthorizationDto(user, null);
        return responseHandler.sendResponse(data, HttpStatus.CREATED, null, null, "uploaded Successfully");
    }

}
